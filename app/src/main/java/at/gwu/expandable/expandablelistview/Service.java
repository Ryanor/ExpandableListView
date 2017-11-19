package at.gwu.expandable.expandablelistview;


import java.util.List;

public class Service {

    private String UUID;
    private List<Characteristic> characteristicList;

    public Service(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public List<Characteristic> getCharacteristicList() {
        return characteristicList;
    }

    public void setCharacteristicList(List<Characteristic> characteristicList) {
        this.characteristicList = characteristicList;
    }
}
