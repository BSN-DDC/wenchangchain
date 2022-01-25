package ai.bianjie.ddc.exception;


//import com.alibaba.fastjson.JSONObject;
import ai.bianjie.ddc.constant.ErrorMessage;

public class DDCException extends RuntimeException{
    private int code;
    private String msg;

    public DDCException(ErrorMessage errorMessage) {
        this.code = errorMessage.getCode();
        this.msg = errorMessage.getMessage();
    }

    public DDCException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
