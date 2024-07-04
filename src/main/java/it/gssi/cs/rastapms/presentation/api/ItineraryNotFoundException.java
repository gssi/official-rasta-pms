package it.gssi.cs.rastapms.presentation.api;

public class ItineraryNotFoundException extends RuntimeException {

    public ItineraryNotFoundException() {
    }

    public ItineraryNotFoundException(String message) {
        super(message);
    }

    public ItineraryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItineraryNotFoundException(Throwable cause) {
        super(cause);
    }

    public ItineraryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
