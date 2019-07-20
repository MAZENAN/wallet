package cn.part.wallet.entity;

public class WalletInfo {
    private String walletName = "";
    private String address = "";
    private String balance = "";
    private String Id = "";
    public WalletInfo(String walletName, String address, String balance,String ID) {
        this.walletName = walletName;
        this.address = address;
        this.balance = balance;
        this.Id = ID;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getId() {
        return Id;
    }
}
