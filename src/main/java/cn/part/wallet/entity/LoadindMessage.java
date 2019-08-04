package cn.part.wallet.entity;

public class LoadindMessage {
    private Boolean isLoading;
    private String message;

    public LoadindMessage(String message, Boolean loading) {
        this.message = message;
        this.isLoading = loading;
    }

    public void setLoading(Boolean loading) {
        isLoading = loading;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getLoading() {
        return isLoading;
    }

    public String getMessage() {
        return message;
    }
}
