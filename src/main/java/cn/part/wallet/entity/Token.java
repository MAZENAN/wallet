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

    public Token(){}

    public Token(String tokenName,BigDecimal tokenNum,BigDecimal tokenToCoin,String address,String contractaddress) {
        this.tokenName = tokenName;
        this.tokenNum = tokenNum;
        this.tokenToCoin = tokenToCoin;
        this.address = address;
        this.contractaddress = contractaddress;
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

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
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
            return token;
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
