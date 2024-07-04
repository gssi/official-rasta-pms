package it.gssi.cs.rastapms.presentation.api;

public class POINotFoundException extends RuntimeException {

    public POINotFoundException() {
    }

    public POINotFoundException(String message) {
        super(message);
    }

    public POINotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public POINotFoundException(Throwable cause) {
        super(cause);
    }

    public POINotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
