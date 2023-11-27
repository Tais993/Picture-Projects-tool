package nl.tijsbeek.pictureprojectstool.entities.files;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import nl.tijsbeek.pictureprojectstool.entities.pp3.PP3File;

import java.io.IOException;
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
    private Metadata metadata;
    private Orientation orientation;

    public Image(Path imagePath) {
        this.imagePath = imagePath;

        try {
            String fileName = imagePath.getFileName().toString();

            if (fileName.contains(".")) {
                this.extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                this.extension = null;
            }


            if (!isDisplayType()) return;

            this.metadata = ImageMetadataReader.readMetadata(imagePath.toFile());

            // obtain the Exif directory
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            // query the tag's value
            int orientationInt = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
            this.orientation = Orientation.getOrientationFromMetadata(orientationInt);

            reload();

        } catch (MetadataException | ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        Path pp3Path = imagePath.resolveSibling(imagePath.getFileName().toString() + ".pp3");
        pp3File = PP3File.read(pp3Path);
    }

    public Path getImagePath() {
        return imagePath;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public PP3File getPP3() {
        return pp3File;
    }

    public boolean isDisplayType() {
        return DISPLAY_TYPES.contains(extension);
    }
}
