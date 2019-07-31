package cn.part.wallet.service.response.btctest;

/**
 * btc doge 测试 返回未交易tx
 */
public class Tx {

    /**
     * txid : 5e72c998e6970daeb19ff333aa908d5d4cbe7b1bdcaac945b09048f99217256f
     * output_no : 0
     * script_asm : OP_DUP OP_HASH160 5a71947d9792a3f827ef466a4f93d6c569fedc20 OP_EQUALVERIFY OP_CHECKSIG
     * script_hex : 76a9145a71947d9792a3f827ef466a4f93d6c569fedc2088ac
     * value : 0.07508551
     * confirmations : 614
     * time : 1564141411
     */

    private String txid;
    private int output_no;
    private String script_asm;
    private String script_hex;
    private String value;
    private int confirmations;
    private int time;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getOutput_no() {
        return output_no;
    }

    public void setOutput_no(int output_no) {
        this.output_no = output_no;
    }

    public String getScript_asm() {
        return script_asm;
    }

    public void setScript_asm(String script_asm) {
        this.script_asm = script_asm;
    }

    public String getScript_hex() {
        return script_hex;
    }

    public void setScript_hex(String script_hex) {
        this.script_hex = script_hex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
