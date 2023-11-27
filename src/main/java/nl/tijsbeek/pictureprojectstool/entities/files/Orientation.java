package nl.tijsbeek.pictureprojectstool.entities.files;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Orientation {
    _0_DEGREES(1),
    _MIRRORED(2),
    _180_DEGREES(3),
    _180_DEGREES_MIRRORED(4),
    _270_DEGREES_MIRRORED(5),
    _90_DEGREES(6),
    _90_DEGREES_MIRRORED(7),
    _270_DEGREES(8);

    private static final Map<Integer, Orientation> ORIENTATION_TO_METADATA = Arrays.stream(Orientation.values())
            .collect(HashMap::new,
                    (map, orientation1) -> map.put(orientation1.getRawMetaDataValue(), orientation1),
                    HashMap::putAll);

    int rawMetaDataValue;

    Orientation(int i)
    {
        rawMetaDataValue = i;
    }

    public int getRawMetaDataValue() {
        return rawMetaDataValue;
    }

    public static Orientation getOrientationFromMetadata(int rawMetaDataValue) {
        Orientation orientation = ORIENTATION_TO_METADATA.get(rawMetaDataValue);

        if (orientation == null) {
            throw new IllegalArgumentException("The given orientation is unsupported");
        }

        return orientation;
    }
}
