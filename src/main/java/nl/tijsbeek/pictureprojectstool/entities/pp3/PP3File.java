package nl.tijsbeek.pictureprojectstool.entities.pp3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PP3File {

    public static final Pattern categoryPattern = Pattern.compile("(?<start>\\[)(?<name>[^]]+)(?<end>])");

    public static final Pattern itemPattern = Pattern.compile("(?<name>[^=]+)=(?<value>.+)");


    public final Map<String, PP3Category> categories;

    public PP3File(Map<String, PP3Category> categories) {
        this.categories = categories;
    }


    public static PP3File read(Path pp3Path) {

        Map<String, PP3Category> categories = new HashMap<>();

        PP3Category currentCategory = null;

        if (!Files.exists(pp3Path)) {
            return null;
        }


        try {
            for (String line : Files.readAllLines(pp3Path)) {
                Matcher categoryMatcher = categoryPattern.matcher(line);
                Matcher itemMatcher = itemPattern.matcher(line);

                if (categoryMatcher.matches()) {
                    String name = categoryMatcher.group("name");

                    PP3CategoryImpl category = new PP3CategoryImpl(name);
                    categories.put(name, category);
                    currentCategory = category;

                } else if (itemMatcher.matches()) {
                    String name = itemMatcher.group("name");
                    String value = itemMatcher.group("value");

                    currentCategory.addItem(new PP3ItemImpl(name, value));

                } else if (line.isBlank()) {
                    currentCategory = null;
                }
            }
        } catch (IOException e) {
            return null;
        }

        return new PP3File(categories);
    }
}
