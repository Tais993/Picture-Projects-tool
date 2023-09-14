package nl.tijsbeek.pictureprojectstool.entities.files;

import nl.tijsbeek.pictureprojectstool.entities.pp3.PP3File;

import java.nio.file.Path;
import java.util.Set;

public class Image {

    private static final Set<String> DISPLAY_TYPES = Set.of(
            "PNG",
            "JPEG",
            "JPG",
            "GIF",
            "BMP"
    );


    //TODO improve image loading
    private final Path imagePath;

    private final String extension;

    private PP3File pp3File;

    public Image(Path imagePath) {
        this.imagePath = imagePath;

        String fileName = imagePath.getFileName().toString();

        if (fileName.contains(".")) {
            this.extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            this.extension = null;
        }

        reload();
    }

    public void reload() {
        Path pp3Path = imagePath.resolveSibling(imagePath.getFileName().toString() + ".pp3");
        pp3File = PP3File.read(pp3Path);
    }

    public Path getImagePath() {
        return imagePath;
    }

    public PP3File getPP3() {
        return pp3File;
    }

    public boolean isDisplayType() {
        return DISPLAY_TYPES.contains(extension);
    }
}
