 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import com.app.taskpage.Task;
import com.app.taskpage.TaskPage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
        // TODO
    }    

    void ready(Stage stage) {
        this.stage = stage;
        
        // Fake journals and pages for testing
        Journal fakeJournalOne = new Journal("Fake Journal One", "Test Des");
        addJournalToAccordian(fakeJournalOne);
        listOfJournals.add(fakeJournalOne);
        Journal fakeJournalTwo = new Journal("Fake Journal Two");
        addJournalToAccordian(fakeJournalTwo);
        TaskPage taskPageOne = new TaskPage("Fake Page One");
        taskPageOne.addTask(new Task("Create a new journal"));
        taskPageOne.addTask(new Task("Delete a journal!"));
        fakeJournalOne.addPage(taskPageOne);
        fakeJournalOne.addPage(new TaskPage("Fake Page Two", "With a Description!"));
    }
    
    public void addJournalToAccordian(Journal journal) {
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
                    System.out.println(newValue);
                    FXMLLoader loader = newValue.getFXMLLoader();
                    Parent root = (Parent) loader.load();
                    PageUIController controller = loader.getController();
                    controller.ready(stage, newValue);
                    scrollPane.setContent((Pane)root);
                } catch (IOException ex) {
                    Logger.getLogger(MainInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void handleSaveJournal() throws IOException {
        if(journalAccordion.getExpandedPane() == null) {
            System.out.println("No Journal Selected");
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try
                {
                    ((JournalTitledPane)journalAccordion.getExpandedPane()).getJournal().saveJournalToFile(file);
                }catch(IOException ioex)
                {
                   String message = "Exception occurred while opening " + file.getPath();
                   System.out.println(message);
                }
            }
        }
    }
    
    public void handleOpenJournal() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try
            {
                String jsonStr = "";
                String line = null;
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while((line = bufferedReader.readLine()) != null) {
                    jsonStr += line;
                }
                System.out.println(jsonStr);
                bufferedReader.close();
            }catch(Exception ex){
//               String message = "Exception occurred while opening " + file.getPath() + "\nError: " + ioex;
               System.out.println(ex.getMessage());
            }
        }
    }
    
    public void handleDebug(ActionEvent event) {
        if(journalAccordion.getExpandedPane() == null) {
            System.out.println("No Journal Selected");
        } else {
            System.out.println(((JournalTitledPane)journalAccordion.getExpandedPane()).getJournal().getName());
        }
    }
}
