/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
        fakeJournalOne.addPage(new Page("Fake Page One"));
        populateTreeView();
    }
    
    public void populateTreeView() {
        TreeItem<String> treeItemRoot = new TreeItem<>("Root");
        
        for(Journal journal : Journal.getJournals()) {
            TreeItem<String> journalLeaf = new TreeItem<>(journal.getName());
            treeItemRoot.getChildren().add(journalLeaf);
            
            for(Page page : journal.getPages()) {
                TreeItem<String> pageLeaf = new TreeItem<>(page.getName());
                journalLeaf.getChildren().add(pageLeaf);
            }
        };
        
        TreeItem<String> nodeItemA = new TreeItem<>("Item A");
        TreeItem<String> nodeItemB = new TreeItem<>("Item B");
        TreeItem<String> nodeItemC = new TreeItem<>("Item C");
        treeItemRoot.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC);
         
        TreeItem<String> nodeItemA1 = new TreeItem<>("Item A1");
        TreeItem<String> nodeItemA2 = new TreeItem<>("Item A2");
        TreeItem<String> nodeItemA3 = new TreeItem<>("Item A3");
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3);

        journalList.setRoot(treeItemRoot);
        journalList.setEditable(true);
    }
    
}
