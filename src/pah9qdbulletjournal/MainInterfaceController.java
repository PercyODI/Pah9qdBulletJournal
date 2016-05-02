 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import com.app.taskpage.Task;
import com.app.taskpage.TaskPage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * FXML Controller class
 *
 * @author pears
 */
public class MainInterfaceController implements Initializable {

    private Stage stage;

    private ArrayList<Journal> listOfJournals = new ArrayList<>();
    
    @FXML
    private Accordion journalAccordion;
    
    @FXML
    private TextArea testingTextArea;
    
    @FXML
    private SplitPane splitPane;
    
    @FXML
    private ScrollPane scrollPane;
    
//    @FXML
//    private Pane pagePane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    void ready(Stage stage) {
        this.stage = stage;
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                if(!journalAccordion.getPanes().isEmpty()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Closing BulletJournal");
                    alert.setHeaderText("You Have Unsaved Journals");
                    alert.setContentText("What would you like to do");

                    ButtonType saveJournalBtn = new ButtonType("Save Journals");
                    ButtonType closeWithoutSavingBtn = new ButtonType("Close Without Saving");
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(saveJournalBtn, closeWithoutSavingBtn, cancelBtn);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == saveJournalBtn){
                        for(TitledPane journalPane : journalAccordion.getPanes()) {
                            Journal journal = ((JournalTitledPane)journalPane).getJournal();
                            saveJournal(journal);
                        }
                    } else if (result.get() == closeWithoutSavingBtn) {
                        // No Saving Occurs
                    } else {
                        we.consume();
                    }
                }
            }
        });
    }
    
    public JournalTitledPane addJournalToAccordian(Journal journal) {
        ListView<Page> listView = new ListView<>(journal.getPages());
        
        JournalTitledPane titledPage = new JournalTitledPane(journal.getName(), listView);
        titledPage.setJournal(journal);
        
        journal.setTitledPane(titledPage);
        journalAccordion.getPanes().add(journal.getTitledPane());            

        // Each ListView needs this CellFactory set to hold the Pages and
        // display their names in the list
        listView.setCellFactory(param -> new ListCell<Page>() {
            protected void updateItem(Page item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // This sets off a function (changed()) everytime a page is selected
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Page>() {
            @Override
            public void changed(ObservableValue<? extends Page> observable, Page oldValue, Page newValue) {
                try {
                    FXMLLoader loader;
                    Parent root;
                    if(newValue.getFxmlLoader() == null) {
                        loader = newValue.createFXMLLoader();
                        root = (Parent) loader.load();
                        PageUIController controller = loader.getController();
                        controller.ready(stage, newValue);
                    } else {
                        loader = newValue.getFxmlLoader();
                        root = (Parent) loader.load();
                    }
                    scrollPane.setContent((Pane)root);
                } catch (IOException ex) {
                    Logger.getLogger(MainInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        journalAccordion.setExpandedPane((TitledPane)titledPage);
        return titledPage;
    }
    
    public void handleRenameJournal() {
        if(journalAccordion.getExpandedPane() == null) {
            displayNoJournalSelectedAlert();
        } else {
            Journal renamingJournal = getCurrentSelectedJournal();
            TextInputDialog dialog = new TextInputDialog(renamingJournal.getName());
            dialog.setTitle("Rename Journal");
            dialog.setHeaderText("Renaming a Journal");
            dialog.setContentText("New Journal Name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                renamingJournal.setName(name);
                journalAccordion.getExpandedPane().setText(name);
            });
        }
    }
    
    public void handleNewJournal() {
        TextInputDialog dialog = new TextInputDialog("Default");
        dialog.setTitle("New Journal");
        dialog.setHeaderText("New Journal");
        dialog.setContentText("New Journal Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Journal newJournal = new Journal(name);
            JournalTitledPane journalTitledPane = addJournalToAccordian(newJournal);
            newJournal.addPage(new TaskPage("Default Task Page"));
        });
    }
    
    public void handleSaveJournal() throws IOException {
        if(journalAccordion.getExpandedPane() == null) {
            displayNoJournalSelectedAlert();
        } else {
            Journal savingJournal = getCurrentSelectedJournal();
            saveJournal(savingJournal);
        }
    }
    
    public void saveJournal(Journal journal) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        fileChooser.setInitialFileName(journal.getName());
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try
            {
                journal.saveJournalToFile(file);
            }catch(IOException ioex)
            {
               String message = "Exception occurred while opening " + file.getPath();
               displayErrorAlert(message);
            }
        }
    }
    
    public void handleOpenJournal() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            JSONParser parser = new JSONParser();
            try
            {
                Object obj = parser.parse(new FileReader(file));
                JSONObject jsonObject = (JSONObject) obj;
                Journal openJournal = Journal.loadJournalFromJsonObject(jsonObject);
                
                addJournalToAccordian(openJournal);
            }catch(Exception ex){
               String message = "Exception occurred while opening " + file.getPath() + "\nError: " + ex;
               displayErrorAlert(message);
            }
        }
    }
    
    public void handleCloseJournal() {
        JournalTitledPane journalTitledPane = (JournalTitledPane)journalAccordion.getExpandedPane();
        Journal journalToClose = journalTitledPane.getJournal();
        saveJournal(journalToClose);
        journalAccordion.getPanes().remove((TitledPane)journalTitledPane);
    }
    
    // Task List Menus methods
    public void handleNewTaskList() {
        if(journalAccordion.getExpandedPane() == null) {
            displayNoJournalSelectedAlert();
        } else {
            TextInputDialog dialog = new TextInputDialog("Default");
            dialog.setTitle("New Task List");
            dialog.setHeaderText("New Tas List");
            dialog.setContentText("New Task List Name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                TaskPage newTaskPage = new TaskPage(name);
                getCurrentSelectedJournal().addPage(newTaskPage);
            });
        }
    }
    
    private void displayNoJournalSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Journal Selection");
        alert.setHeaderText(null);
        alert.setContentText("Please select a journal to complete this action");

        alert.showAndWait();
    }
    
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        //alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Journal getCurrentSelectedJournal() {
        return ((JournalTitledPane)journalAccordion.getExpandedPane()).getJournal();
    }
}
