package pyg.com.utis;

import java.io.Serializable;

/**
 * 2018.12.4 9点56    实体类没有序列化 页面接收不到message
 */
public class PygResult implements Serializable {


    public PygResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private boolean success;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
