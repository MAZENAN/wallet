package cn.part.wallet.entity;

import org.litepal.crud.LitePalSupport;

public class AddressBook extends LitePalSupport {
    private String name;
    private String type;
    private String address;
    private String remark;

    public AddressBook() {

    }

    public AddressBook(String name, String type, String address, String remark) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.remark = remark;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getRemark() {
        return remark;
    }
}
