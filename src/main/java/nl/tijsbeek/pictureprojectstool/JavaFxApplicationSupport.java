package nl.tijsbeek.pictureprojectstool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import nl.tijsbeek.pictureprojectstool.db.DbHandler;
import nl.tijsbeek.pictureprojectstool.javafx.StageReadyEvent;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class JavaFxApplicationSupport extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder()
                .sources(PictureProjectsToolsApplication.class)
                .run(getParameters().getRaw().toArray(new String[0]));

        DbHandler.enableDatabase(context);
    }

    @Override
    public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
}