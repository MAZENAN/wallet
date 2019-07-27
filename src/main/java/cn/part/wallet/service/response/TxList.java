package cn.part.wallet.service.response;

import java.util.List;
import cn.part.wallet.entity.TxInfo;

public class TxList {
    /**
     * status : 200
     * data : {"history_list":[{"outgoing":0,"incoming":"3000000000000000000","state":"1","time":"2019-07-23 19:48:24","txid":"0x0c589863fe9b5513e61f99bcf50b000f6970f9f848e5dd636c7bf56d159ca146"},{"outgoing":0,"incoming":"18750000000000000000","state":"1","time":"2019-07-24 08:27:55","txid":"0x763890ea65bd076816395dc0d3d565eab9edaf216a33783015d1adc55a85052b"},{"outgoing":"1000000000000000000","incoming":0,"state":"1","time":"2019-07-24 16:08:55","txid":"0xd6080b9a5c74aa2f60836ec92429ec44b908604eaf8c88bc14e69078ea396661"},{"outgoing":"990000000000000000","incoming":0,"state":"1","time":"2019-07-24 16:34:10","txid":"0xdecde839ac87ee5b94c8315bf6dcdc5f1b640be29da2449e46a3ae1d3973d5a0"},{"outgoing":"0","incoming":0,"state":"1","time":"2019-07-24 17:02:25","txid":"0xc502c415379660f3e51e577da6970c1f38c136cad83cf0bd6867d1243b92f4b4"},{"outgoing":"1000000000000000000","incoming":0,"state":"1","time":"2019-07-24 17:02:25","txid":"0x6c0c2048e5928d459d90076fecffe96105e01a9fc43fa7fcc02501a00103064a"},{"outgoing":"1000000000000000000","incoming":0,"state":"1","time":"2019-07-24 17:08:25","txid":"0x2a428404a0057427f374d622a4c0fe59c3137662ab151b6299fc59060030295a"},{"outgoing":"2000000000000000000","incoming":0,"state":"1","time":"2019-07-24 17:09:55","txid":"0x1486249932c5eaf0c9429ce8e4f56159987f3453e547f8318f10ca2243dbc1f2"},{"outgoing":"5000000000000000000","incoming":0,"state":"1","time":"2019-07-24 17:10:25","txid":"0xa148ba17f868560562852c061ac586591dff3638b4779fca3bcc012dd79f9d19"}]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TxInfo> history_list;

        public List<TxInfo> getHistory_list() {
            return history_list;
        }

        public void setHistory_list(List<TxInfo> history_list) {
            this.history_list = history_list;
        }
    }
}
