package pro.sky.sockswarehouse.util;

public class SocksErrorResponse {
    private String message;

    public SocksErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
