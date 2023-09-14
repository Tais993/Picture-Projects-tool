package nl.tijsbeek.pictureprojectstool;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.tijsbeek.pictureprojectstool.entities.Project;

import java.io.IOException;

public class ProjectControl extends GridPane {
    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectPath;

    private Project project;

    public ProjectControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            ProjectView root = new ProjectView();
            root.setProject(project);
            root.reload();

            Scene scene = new Scene(root, 320, 240);

            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        });
    }

    public ProjectControl(String projectName, String projectPath) {
        this();

        setProjectName(projectName);
        setProjectPath(projectPath);
    }

    public ProjectControl(Project project) {
        this(project.getName(), project.getProjectPath().toString());
    }

    public String getProjectName() {
        return projectNameProperty().get();
    }

    public void setProjectName(String value) {
        projectNameProperty().set(value);
    }

    public StringProperty projectNameProperty() {
        return projectNameLabel.textProperty();
    }

    public String getProjectPath() {
        return projectPathProperty().get();
    }

    public void setProjectPath(String value) {
        projectPathProperty().set(value);
    }

    public StringProperty projectPathProperty() {
        return projectPath.textProperty();
    }

    public void setProject(Project project) {
        this.project = project;
        setProjectPath(project.getProjectPath().toString());
        setProjectName(project.getName());
    }
}

