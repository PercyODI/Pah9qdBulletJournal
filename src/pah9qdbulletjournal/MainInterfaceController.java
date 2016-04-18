/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBoxTreeItem;
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
        
        populateTreeView();
    }
    
    public void populateTreeView() {
        CheckBoxTreeItem<String> treeItemRoot = new CheckBoxTreeItem<>("Root");
        
//        CheckBoxTreeItem<String> nodeItemA = new CheckBoxTreeItem<>("Item A");
//        CheckBoxTreeItem<String> nodeItemB = new CheckBoxTreeItem<>("Item B");
//        CheckBoxTreeItem<String> nodeItemC = new CheckBoxTreeItem<>("Item C");
//        treeItemRoot.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC);
//         
//        CheckBoxTreeItem<String> nodeItemA1 = new CheckBoxTreeItem<>("Item A1");
//        CheckBoxTreeItem<String> nodeItemA2 = new CheckBoxTreeItem<>("Item A2");
//        CheckBoxTreeItem<String> nodeItemA3 = new CheckBoxTreeItem<>("Item A3");
//        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3);
        
        journalList.setCellFactory(CheckBoxTreeCell.<String>forTreeView());    
        for (int i = 0; i < 8; i++) {
            final CheckBoxTreeItem<String> checkBoxTreeItem = 
                new CheckBoxTreeItem<>("Sample" + (i+1));
                    treeItemRoot.getChildren().add(checkBoxTreeItem);   
        }

        journalList.setRoot(treeItemRoot);
        journalList.setEditable(true);
    }
    
}
