package nl.tijsbeek.pictureprojectstool.entities;

import nl.tijsbeek.pictureprojectstool.entities.files.Image;

public interface ImageEventListener {
    void initializeEvent();
    void onImageCreate(ImageCreateEvent event);
    void onImageUpdate(ImageEvent event);
    void onImageDelete(ImageEvent event);


    public class ImageCreateEvent implements ImageEvent {
        private final Image image;

        public ImageCreateEvent(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return image;
        }
    }

    public interface ImageEvent {

    }
}

