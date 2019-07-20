package cn.part.wallet.entity;

public class IdentityInfo {
    private String name;
    private String ID;

    public IdentityInfo(String name,String ID) {
        this.name = name;
        this.ID = ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
