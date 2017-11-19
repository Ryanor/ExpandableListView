package at.gwu.expandable.expandablelistview;


import java.util.List;

public class Characteristic {

    private String UUID;
    private List<Descriptor> descriptorList;
    private int value;

    public Characteristic(String UUID, int value) {
        this.UUID = UUID;
        this.value = value;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public List<Descriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<Descriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAmountOfDescrptors() {
        return descriptorList.size();
    }
}
