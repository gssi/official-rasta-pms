package it.gssi.cs.rastapms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rastapms")
public class RastaPMSConfigProperties {
    private String influxdbURL;
    private String orgID;
    private String authorizationtoken;
    private String bucket;
    private String medescription;
    private String mecategories;

    public String getInfluxdbURL() {
        return influxdbURL;
    }

    public void setInfluxdbURL(String influxdbURL) {
        this.influxdbURL = influxdbURL;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getAuthorizationtoken() {
        return authorizationtoken;
    }

    public void setAuthorizationtoken(String authorizationtoken) {
        this.authorizationtoken = authorizationtoken;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getMedescription() {
        return medescription;
    }

    public void setMedescription(String medescription) {
        this.medescription = medescription;
    }

    public String getMecategories() {
        return mecategories;
    }

    public void setMecategories(String mecategories) {
        this.mecategories = mecategories;
    }
}
