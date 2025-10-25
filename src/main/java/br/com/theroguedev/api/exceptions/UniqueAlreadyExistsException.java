package br.com.theroguedev.api.exceptions;

public class UniqueAlreadyExistsException extends RuntimeException {
    public UniqueAlreadyExistsException(String message) {
        super(message);
    }
}
