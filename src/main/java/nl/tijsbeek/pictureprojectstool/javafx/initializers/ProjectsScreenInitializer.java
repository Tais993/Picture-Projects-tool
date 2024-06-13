package nl.tijsbeek.pictureprojectstool.javafx.initializers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import nl.tijsbeek.pictureprojectstool.javafx.ProjectManager;
import nl.tijsbeek.pictureprojectstool.javafx.ProjectsScreen;
import nl.tijsbeek.pictureprojectstool.javafx.StageReadyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProjectsScreenInitializer extends AnchorPane implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @FXML
    public VBox projectsVBox;

    private ProjectManager projectManager = new ProjectManager();


    @Autowired
    public ProjectsScreenInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;

        Scene scene = new Scene(fxWeaver.loadView(ProjectsScreen.class));
        stage.setResizable(true);
        stage.setMinHeight(260);
        stage.setMinWidth(200);
        stage.setScene(scene);
        stage.show();
    }
}
