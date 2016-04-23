/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.taskpage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import pah9qdbulletjournal.Page;
import pah9qdbulletjournal.PageUIController;

/**
 * FXML Controller class
 *
 * @author pears
 */
public class TaskPageUIController implements Initializable, PageUIController{
    
    private Stage mainStage;
    private TaskPage taskPage;
    
    @FXML
    private ListView taskListView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Use ready() for any items that require the use of taskPage
    }
    
    @Override
    public void ready(Stage mainStage, Page page) {
        this.mainStage = mainStage;
        taskPage = (TaskPage)page;
        
        taskListView.setItems(taskPage.getTasks());
        
        // Turns the ListView into a checkbox list, while still displaying
        // the names of the tasks. Tasks completion is handled by this as well!
        taskListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Task, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Task task) {
                return task.getCompleted();
            }
        }, new StringConverter<Task>() {
            @Override
            public String toString(Task task) {
                return task.getName();
            }
            
            @Override
            public Task fromString(String string) {
                return null;
            }
        }));
    }
    
    public void showNewTaskWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTaskUI.fxml"));
        Parent root = (Parent) loader.load();
        NewTaskUIController controller = loader.getController();
        
        controller.ready(taskPage);
        
        stage.setScene(new Scene(root));
        stage.setTitle("Add a New Task");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
    }
    
    public void handleRemoveTask() {
        taskPage.removeTask((Task)taskListView.getSelectionModel().getSelectedItem());
    }
}
