package it.gssi.cs.rastapms.presentation.api;

public class ItineraryException extends RuntimeException {

    public ItineraryException() {
    }

    public ItineraryException(String message) {
        super(message);
    }

    public ItineraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItineraryException(Throwable cause) {
        super(cause);
    }

    public ItineraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
