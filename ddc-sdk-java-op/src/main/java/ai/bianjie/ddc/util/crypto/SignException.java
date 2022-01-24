package ai.bianjie.ddc.util.crypto;

public class SignException extends RuntimeException  {
    private String message;

    public SignException(){  }

    public SignException(String msg){
        this.message = msg;
    }
}
