package cn.part.wallet.entity;

import android.os.Parcel;
import android.os.Parcelable;

public  class TransInfo  implements Parcelable{
    private String blockNumber;
    private String timeStamp;
    private String hash;
    private String nonce;
    private String blockHash;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gas;
    private String gasPrice;
    private String isError;
    private String txreceipt_status;
    private String input;
    private String contractAddress;
    private String cumulativeGasUsed;
    private String gasUsed;
    private String confirmations;

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getTxreceipt_status() {
        return txreceipt_status;
    }

    public void setTxreceipt_status(String txreceipt_status) {
        this.txreceipt_status = txreceipt_status;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(blockNumber);
        parcel.writeString(timeStamp);
        parcel.writeString(hash);
        parcel.writeString(nonce);
        parcel.writeString(blockHash);
        parcel.writeString(transactionIndex);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(value);
        parcel.writeString(gas);
        parcel.writeString(gasPrice);
        parcel.writeString(isError);
        parcel.writeString(txreceipt_status);
        parcel.writeString(input);
        parcel.writeString(contractAddress);
        parcel.writeString(cumulativeGasUsed);
        parcel.writeString(gasUsed);
        parcel.writeString(confirmations);
    }
    public static final Parcelable.Creator<TransInfo> CREATOR = new Creator<TransInfo>(){
        @Override
        public TransInfo createFromParcel(Parcel parcel) {
            TransInfo transInfo =  new TransInfo();
            transInfo.blockNumber = parcel.readString();
            transInfo.timeStamp = parcel.readString();
            transInfo.hash = parcel.readString();
            transInfo.nonce = parcel.readString();
            transInfo.blockHash = parcel.readString();
            transInfo.transactionIndex = parcel.readString();
            transInfo.from = parcel.readString();
            transInfo.to = parcel.readString();
            transInfo.value = parcel.readString();
            transInfo.gas = parcel.readString();
            transInfo.gasPrice = parcel.readString();
            transInfo.isError = parcel.readString();
            transInfo.txreceipt_status = parcel.readString();
            transInfo.input = parcel.readString();
            transInfo.contractAddress = parcel.readString();
            transInfo.cumulativeGasUsed = parcel.readString();
            transInfo.gasUsed = parcel.readString();
            transInfo.confirmations  = parcel.readString();
            return transInfo;
        }

        @Override
        public TransInfo[] newArray(int i) {
            return new TransInfo[i];
        }
    };
}
