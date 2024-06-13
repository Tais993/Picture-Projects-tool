package nl.tijsbeek.pictureprojectstool.javafx;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import nl.tijsbeek.pictureprojectstool.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@FxmlView
public class ProjectsScreen extends AnchorPane {

    private final FxWeaver fxWeaver;

    @FXML
    public VBox projectsVBox;

    private ProjectManager projectManager = new ProjectManager();


    @Autowired
    public ProjectsScreen(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        projectManager.addProject(new Project("Terzij De Horde", Path.of("C:\\Users\\tijs\\OneDrive - Office 365 Fontys\\Desktop\\Backup important\\Pictures\\.De Pul\\2024-6-01-Terzij De Horde & RadeloosZiedend")));
//        projectManager.addProject(new Project("Phil Bee Cocker Band", Path.of("C:\\Users\\tijs\\OneDrive - Office 365 Fontys\\Desktop\\Backup important\\Pictures\\.De Pul\\2024-6-01-Phil Bee Cocker Band")));

        Collection<Project> projects = projectManager.getProjects();

        List<Node> controls = projects
                .stream()
                .map(project -> {
                    FxControllerAndView<ProjectControl, Node> load = fxWeaver.load(ProjectControl.class);

                    load.getController().setProject(project);
                    return load.getView();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        projectsVBox.getChildren().addAll(controls);
    }
}