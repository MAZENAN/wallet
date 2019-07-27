package cn.part.wallet.entity;

public class TxInfo {
    /**
     * outgoing : 0
     * incoming : 0.07508551
     * state : 1
     * time : 2019-07-26 19:41:02
     * txid : 5e72c998e6970daeb19ff333aa908d5d4cbe7b1bdcaac945b09048f99217256f
     */

    private String outgoing;
    private String incoming;
    private int state;
    private String time;
    private String txid;

    public String getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}
