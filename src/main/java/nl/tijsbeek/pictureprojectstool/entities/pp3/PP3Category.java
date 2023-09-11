package nl.tijsbeek.pictureprojectstool.entities.pp3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

public interface PP3Category {
    @NotNull String getName();

    @UnmodifiableView Map<String, PP3Item> items();

    void addItem(PP3Item item);

    @NotNull PP3Item getItem(String name);

    default void removeItem(PP3Item item) {
        removeItem(item.getName());
    }

    void removeItem(String name);
}
