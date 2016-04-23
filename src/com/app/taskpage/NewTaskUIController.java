/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.taskpage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pears
 */
public class NewTaskUIController implements Initializable{
    private TaskPage taskPage;
    
    @FXML
    private TextField name;
   
    @FXML 
    private TextField description;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Use ready() for any items that require the use of taskPage
    }
    
    public void ready(TaskPage taskPage) {
        this.taskPage = taskPage;
    }
    
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void handleAddTask(ActionEvent event) {
        taskPage.addTask(new Task(name.getText(), description.getText()));
        closeWindow(event);
    }
    
}
