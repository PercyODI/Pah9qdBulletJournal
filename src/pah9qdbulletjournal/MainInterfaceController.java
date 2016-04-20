/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
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
        Journal fakeJournalOne = new Journal("Fake Journal One");
        Journal fakeJournalTwo = new Journal("Fake Journal Two");
        fakeJournalOne.addPage(new TaskPage("Fake Page One"));
        fakeJournalOne.addPage(new TaskPage("Fake Page Two", "With a Description!"));
        initializeAccordian();
    }
    
    public void initializeAccordian() {
        for(Journal journal : Journal.getJournals()) {
            
            ListView<Page> listView = new ListView<>(journal.getPages());
            journal.setTitledPane(new TitledPane(journal.getName(), listView));
            journalAccordion.getPanes().add(journal.getTitledPane());            
            
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
            
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Page>() {
                @Override
                public void changed(ObservableValue<? extends Page> observable, Page oldValue, Page newValue) {
                    testingTextArea.appendText(newValue.getName() + " Selected from " + newValue.getMyJournal().getName() + "\n");
                }
            });
        }
    }
    
    public void addRandomPage() {
        Random rnd = new Random();
        Journal.getJournals().get(0).addPage(new TaskPage("Page " + rnd.nextInt(100)));
    }
    
}
