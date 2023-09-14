package nl.tijsbeek.pictureprojectstool;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import nl.tijsbeek.pictureprojectstool.entities.Project;
import nl.tijsbeek.pictureprojectstool.entities.files.Image;

import java.io.IOException;
import java.nio.file.Path;

public class ProjectView extends AnchorPane {

    @FXML
    public FlowPane imagesList;

    @FXML
    public ImageView displayImage;

    private Project project;

    public ProjectView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        displayImage.setImage(imageByImagePath("F:\\Backup important\\Pictures\\2023-8-16-Vakantie OG\\Original\\_DSC9165.JPG"));
        displayImage.setFitHeight(130);
//        displayImage.setFitHeight(712);
        displayImage.setFitWidth(200);
//        displayImage.setFitWidth(1072);
    }

    public javafx.scene.image.Image imageByImagePath(String imagePath) {
        return new javafx.scene.image.Image(imagePath, 100, 0, true, false);
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

        imagesList.getChildren().addAll(project.getOriginalFolder().getImages()
                .values()
                .stream()
                .filter(Image::isDisplayType)
                .map(Image::getImagePath)
                .map(this::imageByImagePath)
                .map(ImageView::new)
                .map(imageView -> {
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(200);
                    imageView.setStyle("""
                            -fx-padding: 50,50,50,50;
                            """);

                    return imageView;
                })
                .toList());
    }
}
