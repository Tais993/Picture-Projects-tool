package nl.tijsbeek.pictureprojectstool;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.tijsbeek.pictureprojectstool.entities.Project;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class Projects extends AnchorPane {

    @FXML
    public VBox projectsVBox;

    private ProjectManager projectManager = new ProjectManager();

    public Projects() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Projects.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        projectManager.addProject(new Project("Vakantie", Path.of("D:\\Backup important\\Pictures\\2023-11-24-De Pul")));
//        projectManager.addProject(new Project("Moon", Path.of("F:\\Backup important\\Pictures\\2023-8-31-Moon")));
//        projectManager.addProject(new Project("Vakantie Vertical", Path.of("F:\\Backup important\\Pictures\\2023-8-16-Vakantie OG\\.12")));

        Collection<Project> projects = projectManager.getProjects();

        List<ProjectControl> controls = projects
                .stream()
                .map(project -> {
                    ProjectControl projectControl = new ProjectControl();
                    projectControl.setProject(project);
                    return projectControl;
                })
                .toList();

        projectsVBox.getChildren().addAll(controls);
    }
}