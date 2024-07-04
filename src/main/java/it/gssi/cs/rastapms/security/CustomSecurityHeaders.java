package it.gssi.cs.rastapms.security;

public enum CustomSecurityHeaders {

    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    CustomSecurityHeaders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
