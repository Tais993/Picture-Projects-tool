package nl.tijsbeek.pictureprojectstool.entities;

import nl.tijsbeek.pictureprojectstool.entities.files.Folder;
import nl.tijsbeek.pictureprojectstool.entities.files.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Project {

    private String name;
    private Path projectPath;

    private Map<String, Folder> folders = new HashMap<>();

    private Folder originalFolder;
    private Folder editFolder;
    private Folder finalFolder;


    public Project(String name, Path projectPath) {
        this.name = name;
        this.projectPath = projectPath;
        reload();
    }

    public static void createProject() {
        // TODO
    }

    public void reload() {
        try (Stream<Path> subPaths = Files.list(projectPath)) {
            subPaths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    folders.put(path.getFileName().toString(), new Folder(path));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        editFolder = folders.get("Editing");
        finalFolder = folders.get("Final");
        originalFolder = folders.get("Original");
    }



    public String getName() {
        return name;
    }

    public Path getProjectPath() {
        return projectPath;
    }

    public Map<String, Folder> getFolders() {
        return folders;
    }

    public Folder getOriginalFolder() {
        return originalFolder;
    }

    public Folder getEditFolder() {
        return editFolder;
    }

    public Folder getFinalFolder() {
        return finalFolder;
    }
}
