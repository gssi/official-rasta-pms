package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "parameters")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_id")
    private Long id;

    @JsonView({Views.POIPublic.class})
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParameterType type;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    @NotNull(message = "sensor is mandatory")
    private Sensor sensor;

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
