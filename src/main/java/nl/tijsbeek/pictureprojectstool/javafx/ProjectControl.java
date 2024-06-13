package nl.tijsbeek.pictureprojectstool.javafx;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import nl.tijsbeek.pictureprojectstool.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@FxmlView
public class ProjectControl extends GridPane {

    private final FxWeaver fxWeaver;


    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectPath;

    private Project project;

    @Autowired
    public ProjectControl(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    public void onMouseClick() {

        FxControllerAndView<ProjectView, Node> fxLoad = fxWeaver.load(ProjectView.class);

        ProjectView root = fxLoad.getController();
        Node view = fxLoad.getView().orElseThrow();
        root.setProject(project);
        root.reload();
        Scene scene = new Scene((AnchorPane) view, 320, 240);

        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

//    public ProjectControl(String projectName, String projectPath) {
//        this();
//
//        setProjectName(projectName);
//        setProjectPath(projectPath);
//    }

//4

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

