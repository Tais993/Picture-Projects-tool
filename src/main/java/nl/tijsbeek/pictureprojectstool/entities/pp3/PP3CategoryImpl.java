package nl.tijsbeek.pictureprojectstool.entities.pp3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PP3CategoryImpl implements PP3Category {

    private final String name;
    private final Map<String, PP3Item> items;

    public PP3CategoryImpl(String name) {
        this.name = name;
        this.items = new HashMap<>();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @UnmodifiableView Map<String, PP3Item> items() {
        return Collections.unmodifiableMap(items);
    }

    @Override
    public void addItem(PP3Item item) {
        items.put(item.getName(), item);
    }

    @Override
    public @NotNull PP3Item getItem(String name) {
        return items.get(name);
    }

    @Override
    public void removeItem(String name) {
        items.remove(name);
    }
}
