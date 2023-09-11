package nl.tijsbeek.pictureprojectstool.entities.pp3;

public class PP3ItemImpl implements PP3Item {
    private final String name;
    private final String value;

    public PP3ItemImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAsString() {
        return value;
    }

    @Override
    public double getAsDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public int getAsInt() {
        return Integer.parseInt(value);
    }

    @Override
    public float getAsFloat() {
        return Float.parseFloat(value);
    }
}
