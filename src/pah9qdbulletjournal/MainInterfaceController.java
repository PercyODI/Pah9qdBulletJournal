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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
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
    private TreeView journalList;
    
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
        fakeJournalOne.addPage(new TaskPage("Fake Page One"));
        fakeJournalOne.addPage(new TaskPage("Fake Page Two", "With a Description!"));
        populateTreeView();
    }
    
    public void populateTreeView() {
        TreeItem<String> treeItemRoot = new TreeItem<>("Root");
        
        for(Journal journal : Journal.getJournals()) {
            TreeItem<String> journalLeaf = new TreeItem<>(journal.getName());
            treeItemRoot.getChildren().add(journalLeaf);
            
            journal.setTreeItemParent(treeItemRoot);
            journal.setMyTreeItem(journalLeaf);
            
            for(Page page : journal.getPages()) {
                TreeItem<String> pageLeaf = new TreeItem<>(page.getName());
                journalLeaf.getChildren().add(pageLeaf);
            }
        };

        journalList.setRoot(treeItemRoot);
        journalList.setEditable(true);
    }
    
    public void addTaskPageBtn(ActionEvent event) {
        Random randGen = new Random();
        String randomText = "Page " + randGen.nextInt();
        System.out.println(randomText);
        TaskPage taskPage = new TaskPage(randomText);
        TreeItem<String> treeItemTaskPage = new TreeItem<>(taskPage.getName());
        
        Journal.getJournals().get(0).addPage(taskPage);
        Journal.getJournals().get(0).getMyTreeItem().getChildren().add(treeItemTaskPage);
    }
    
}
