package cn.part.wallet.service.response.btctest;

import java.util.List;

public class TxUnspent {
    /**
     * network : BTCTEST
     * address : momBAzrpTobud7KcsU3UcDVnpaV4U6ajrq
     * txs : [{"txid":"5e72c998e6970daeb19ff333aa908d5d4cbe7b1bdcaac945b09048f99217256f","output_no":0,"script_asm":"OP_DUP OP_HASH160 5a71947d9792a3f827ef466a4f93d6c569fedc20 OP_EQUALVERIFY OP_CHECKSIG","script_hex":"76a9145a71947d9792a3f827ef466a4f93d6c569fedc2088ac","value":"0.07508551","confirmations":614,"time":1564141411}]
     */

    private String network;
    private String address;
    private List<Tx> txs;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Tx> getTxs() {
        return txs;
    }

    public void setTxs(List<Tx> txs) {
        this.txs = txs;
    }
}
