package br.com.fusion.banck.shared.exceptions;

public class FusionApiUserIsSave extends RuntimeException {

    public  FusionApiUserIsSave() {
        super("Error");

    }
    public FusionApiUserIsSave(String message) {
        super(message);
    }

}
