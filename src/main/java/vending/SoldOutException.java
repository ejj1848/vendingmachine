package vending;

/**
 * Created by Eric.Johnson on 5/9/2017.
 */
public class SoldOutException extends RuntimeException {
    private String message;

    public SoldOutException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }
}

