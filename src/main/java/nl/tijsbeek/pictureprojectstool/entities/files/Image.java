package nl.tijsbeek.pictureprojectstool.entities.files;

import nl.tijsbeek.pictureprojectstool.entities.pp3.PP3File;

import java.nio.file.Path;

public class Image {
    private final Path imagePath;

    private PP3File pp3File;

    public Image(Path imagePath) {
        this.imagePath = imagePath;

        reload();
    }

    public void reload() {
        Path pp3Path = imagePath.resolveSibling(imagePath.getFileName().toString() + ".pp3");
        pp3File = PP3File.read(pp3Path);
    }


    public PP3File getPP3() {
        return pp3File;
    }
}
