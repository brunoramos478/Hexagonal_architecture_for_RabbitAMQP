package br.com.fusion.banck.shared.exceptions;

public class NotFoundHours extends RuntimeException {
    public NotFoundHours() {
        super();
    }

    public NotFoundHours(String message) {
        super(message);
    }

}
