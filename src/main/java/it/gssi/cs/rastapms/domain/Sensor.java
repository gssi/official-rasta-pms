package it.gssi.cs.rastapms.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ip_address")
    private String IPAddress;

    @ManyToOne
    @JoinColumn(name = "sensor_type_id", nullable = false)
    private SensorType type;

    @Column(name = "latitude")
    private long latitude;

    @Column(name = "longitude")
    private long longitude;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "sensors_parameters", joinColumns = {@JoinColumn(name = "sensor_id")}, inverseJoinColumns = {
            @JoinColumn(name = "parameter_id")})
    private Set<Parameter> parameters = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "sensors_parameters_values", joinColumns = {@JoinColumn(name = "sensor_id")}, inverseJoinColumns = {
            @JoinColumn(name = "parameter_value_id")})
    private Set<ParameterValue> parametersValues = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Set<ParameterValue> getParametersValues() {
        return parametersValues;
    }

    public void setParametersValues(Set<ParameterValue> parametersValues) {
        this.parametersValues = parametersValues;
    }
}
