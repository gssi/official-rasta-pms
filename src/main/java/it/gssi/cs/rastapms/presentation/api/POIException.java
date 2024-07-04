package it.gssi.cs.rastapms.presentation.api;

public class POIException extends RuntimeException {

    public POIException() {
    }

    public POIException(String message) {
        super(message);
    }

    public POIException(String message, Throwable cause) {
        super(message, cause);
    }

    public POIException(Throwable cause) {
        super(cause);
    }

    public POIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
