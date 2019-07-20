package cn.part.wallet.service.response;

import cn.part.wallet.service.response.EthBase;
/**
 * 以太币余额
 */
public class EthBalance extends EthBase {
    /**
     * result : 0
     */

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
