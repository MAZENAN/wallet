package cn.part.wallet.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Token implements Parcelable {
    private String tokenName;
    private BigDecimal tokenNum;
    private BigDecimal tokenToCoin;
    private String address;
    private String contractaddress;
    private String type;
    private Boolean isToken;

    public Token(){}

    public Token(String tokenName,BigDecimal tokenNum,BigDecimal tokenToCoin,String address,String contractaddress,String type,Boolean isToken) {
        this.tokenName = tokenName;
        this.tokenNum = tokenNum;
        this.tokenToCoin = tokenToCoin;
        this.address = address;
        this.contractaddress = contractaddress;
        this.type = type;
        this.isToken = isToken;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void setTokenNum(BigDecimal tokenNum) {
        this.tokenNum = tokenNum;
    }

    public void setTokenToCoin(BigDecimal tokenToCoin) {
        this.tokenToCoin = tokenToCoin;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
    }

    public void setIsToken(Boolean isToken) {
        this.isToken = isToken;
    }

    public BigDecimal getTokenNum() {
        return tokenNum;
    }

    public String getTokenName() {
        return tokenName;
    }

    public BigDecimal getTokenToCoin() {
        return tokenToCoin;
    }

    public String getAddress() {
        return address;
    }

    public String getContractaddress() {
        return contractaddress;
    }

    public String getType() {
        return type;
    }

    public Boolean getToken() {
        return isToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tokenName);
        parcel.writeString(tokenNum.toString());
        parcel.writeString(tokenToCoin.toString());
        parcel.writeString(address);
        parcel.writeString(contractaddress);
        parcel.writeString(type);
        parcel.writeString(String.valueOf(isToken));
    }

    public static final Parcelable.Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel parcel) {
            Token token = new Token();
            token.tokenName = parcel.readString();
            token.tokenNum = new BigDecimal(parcel.readString());
            token.tokenToCoin = new BigDecimal(parcel.readString());
            token.address = parcel.readString();
            token.contractaddress = parcel.readString();
            token.type = parcel.readString();
            token.isToken = parcel.readString().equals("true");
            return token;
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
