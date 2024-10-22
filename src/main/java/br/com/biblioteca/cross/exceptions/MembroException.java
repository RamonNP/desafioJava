package br.com.biblioteca.cross.exceptions;

public class MembroException extends RuntimeException {
    public MembroException(String message) {
        super(message);
    }

    public MembroException(String message, Throwable cause) {
        super(message, cause);
    }
}
