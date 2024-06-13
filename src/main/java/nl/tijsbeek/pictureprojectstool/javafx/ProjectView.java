package nl.tijsbeek.pictureprojectstool.javafx;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import nl.tijsbeek.pictureprojectstool.entities.ImageEventListener;
import nl.tijsbeek.pictureprojectstool.entities.Project;
import nl.tijsbeek.pictureprojectstool.entities.files.Image;
import nl.tijsbeek.pictureprojectstool.fileloading.ImageSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@FxmlView
public class ProjectView extends AnchorPane implements ImageEventListener {

    private final FxWeaver fxWeaver;

    @FXML
    public FlowPane imagesList;

    @FXML
    public ImageView displayImage;

    private Project project;

    @Autowired
    public ProjectView(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        displayImage.setImage(imageByImagePath("C:\\Users\\tijs\\OneDrive - Office 365 Fontys\\Desktop\\Backup important\\Pictures\\.De Pul\\2024-6-01-Terzij De Horde & RadeloosZiedend\\20240601_RadeloosZiedend_©Tijs_Beek_001"));
        displayImage.setFitHeight(130);
//        displayImage.setFitHeight(712);
        displayImage.setFitWidth(200);
//        displayImage.setFitWidth(1072);
    }

    // TODO file protocol shouldnt be hardcoded, fix?
    public javafx.scene.image.Image imageByImagePath(String imagePath) {
        return new javafx.scene.image.Image("file:///" + imagePath, 100, 0, true, false);
    }

    public javafx.scene.image.Image imageByImagePath(Path imagePath) {
        return imageByImagePath(imagePath.toAbsolutePath().toString());
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void reload() {
        project.reload();

//        imagesList.getChildren().addAll(project.getOriginalFolder().getImages()
//                .values()
//                .stream()
//                .filter(Image::isDisplayType)
//                .map(Image::getImagePath)
//                .map(this::imageByImagePath)
//                .map(ImageView::new)
//                .map(imageView -> {
//                    imageView.setFitHeight(80);
//                    imageView.setFitWidth(200);
//                    imageView.setStyle("""
//                            -fx-padding: 50,50,50,50;
//                            """);
//
//                    return imageView;
//                })
//                .toList());
    }

    @Override
    public void initializeEvent() {

    }

    @Override
    public void onImageCreate(ImageCreateEvent event) {
        Image eventImage = event.getImage();

        if (!eventImage.isDisplayType()) {
            return;
        }

        javafx.scene.image.Image javaFxImage = imageByImagePath(eventImage.getImagePath());
        ImageView imageView = new ImageView(javaFxImage);

        imageView.setFitHeight(80);
        imageView.setFitWidth(200);
        imageView.setStyle("""
                            -fx-padding: 50,50,50,50;
                            """);

        imagesList.getChildren().add(imageView);
    }

    @Override
    public void onImageUpdate(ImageEvent event) {

    }

    @Override
    public void onImageDelete(ImageEvent event) {

    }
}
