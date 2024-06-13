package nl.tijsbeek.pictureprojectstool.fileloading;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import nl.tijsbeek.pictureprojectstool.entities.ImageEventListener;
import nl.tijsbeek.pictureprojectstool.entities.ImageEventListener.ImageCreateEvent;
import nl.tijsbeek.pictureprojectstool.entities.ImageEventListener.ImageEvent;
import nl.tijsbeek.pictureprojectstool.entities.files.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ImageSupplier {

    private final Path directory;

    private List<ImageEventListener> listeners = new ArrayList<>();
    private Set<BigInteger> imageHashes = new HashSet<>();  // Used to check for duplicates

    public ImageSupplier(Path directory) throws IOException, InterruptedException {
        this.directory = directory;

//        watchDirectory(directory);
    }

    public void addFileEventListener(ImageEventListener listener) {
        listeners.add(listener);

        //TODO MOVE TO BETTER SPOT
        initialDirectoryRead(directory);
    }

    public void removeFileEventListener(ImageEventListener listener) {
        listeners.remove(listener);
    }

    private <T extends ImageEvent> void notifyListeners(T event) {
        if (event instanceof ImageCreateEvent imageCreateEvent) {
            for (ImageEventListener listener : listeners) {
                listener.onImageCreate(imageCreateEvent);
            }
        }
    }

    /* TODO THIS IS ALL TEMPORARY AND SHOULD BE MOVED TO A ANOTHER CLASS */
    private void watchDirectory(Path path) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path changed = (Path) event.context();
                String eventType = event.kind().name();
                String fullPath = path.resolve(changed).toString();

                processFileChange(new Image(Path.of(fullPath)), eventType);
//                notifyListeners(new FileEvent(eventType, fullPath));
            }
            key.reset();
        }
    }

    private void initialDirectoryRead(Path basePath) {

        try (Stream<Path> subPaths = Files.list(basePath)) {
            subPaths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        InputStream stream = Files.newInputStream(filePath);


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    notifyListeners(new ImageCreateEvent(new Image(filePath)));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processFileChange(Image image, String eventType) {
        switch (eventType) {
            case "ENTRY_CREATE":
                if (!checkForDuplicates(image)) {
                    updateDatabase(image);
                }
                break;
            case "ENTRY_MODIFY":
                updateDatabase(image);
                break;
            case "ENTRY_DELETE":
                removeFile(image);
                break;
        }
    }

    private void fileCreation(Image image) {
        if (!checkForDuplicates(image)) {
            updateDatabase(image);
        }

        notifyListeners(new ImageCreateEvent(image));
    }
    /* TODO THIS IS ALL TEMPORARY AND SHOULD BE MOVED TO A ANOTHER CLASS */

    private boolean checkForDuplicates(Image image) {
        try {
            BigInteger hash = getHash(image);
            if (imageHashes.contains(hash)) {
                System.out.println("Duplicate file detected: " + image);
                return true;
            } else {
                imageHashes.add(hash);
                return false;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateDatabase(Image image) {
    }

    private void removeFile(Image image) {
    }

    private BigInteger getHash(Image image) throws IOException, NoSuchAlgorithmException {
        HashingAlgorithm hasher = new PerceptiveHash(32);

        Hash hash = hasher.hash(loadImage(image.getImagePath()));

        return hash.getHashValue();
    }

    public static BufferedImage loadImage(Path path) {
        BufferedImage image = null;
        try {
            try (var inputStream = Files.newInputStream(path)) {
                image = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // Additional helper methods as needed
}
