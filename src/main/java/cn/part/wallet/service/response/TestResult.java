package cn.part.wallet.service.response;

/**
 * 测试接口返回泛型
 */
public class TestResult<T> {
        private String status;
        private T data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
