package ai.bianjie.ddc.exception;

import ai.bianjie.ddc.constant.ErrorMessage;

public class DDCException extends RuntimeException {
    private final int code;
    private final String msg;

    public DDCException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
        this.msg = errorMessage.getMessage();
    }

    public DDCException(int code, String msg) {
        super(msg);
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