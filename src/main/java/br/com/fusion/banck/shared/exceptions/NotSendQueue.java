package br.com.fusion.banck.shared.exceptions;

public class NotSendQueue extends RuntimeException {
    public NotSendQueue() {
        super();
    }
    public NotSendQueue(String message) {
        super(message);
    }
}
