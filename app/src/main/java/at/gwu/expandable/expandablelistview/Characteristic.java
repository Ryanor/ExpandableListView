package at.gwu.expandable.expandablelistview;


public class Characteristic {

    private String UUID;
    private String[] properties;
    private String value;

    public Characteristic(String UUID, String[] properties, String value) {
        this.UUID = UUID;
        this.properties = properties;
        this.value = value;
    }

    public String getUUID() {
        return UUID;
    }

    public String getProperties() {
        String propertiesAsOneString = "";
        for(String property : properties) {
            propertiesAsOneString += property + " ";
        }
        return propertiesAsOneString;
    }

    public String getValue() {
        return value;
    }
}
