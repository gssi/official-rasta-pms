package it.gssi.cs.rastapms.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Entity
//@Table(name="parameters_values")
public class ParameterValue {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "parameter_value_id")
    //private Long id;

    @Column(name = "value")
    private String value;

    //@Column(name = "creation_date")
    //private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private Parameter parameter;

    //@ManyToOne
    //@JoinColumn(name = "sensor_id", nullable = false)
    //private Sensor sensor;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
