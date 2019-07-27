package cn.part.wallet.service.response;

public class TokenBalance {
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
        private String confirmed_balance;
        private String unconfirmed_balance;

        public String getConfirmed_balance() {
            return confirmed_balance;
        }

        public void setConfirmed_balance(String confirmed_balance) {
            this.confirmed_balance = confirmed_balance;
        }

        public String getUnconfirmed_balance() {
            return unconfirmed_balance;
        }

        public void setUnconfirmed_balance(String unconfirmed_balance) {
            this.unconfirmed_balance = unconfirmed_balance;
        }
    }
}
