package nl.tijsbeek.pictureprojectstool.entities.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Folder implements Container {

    private Path folderPath;
    private Map<String, Image> images = new HashMap<>();
    private Map<String, Folder> subFolders = new HashMap<>();


    public Folder(Path folderPath) {
        this.folderPath = folderPath;

        try (Stream<Path> subPaths = Files.list(folderPath)) {
            subPaths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    subFolders.put(path.getFileName().toString(), new Folder(path));
                } else if (Files.isRegularFile(path)) {
                    //TODO proper image filtering
                    images.put(path.getFileName().toString(), new Image(path));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Path getFolderPath() {
        return folderPath;
    }

    public Map<String, Image> getImages() {
        return images;
    }

    public Map<String, Folder> getSubFolders() {
        return subFolders;
    }
}
