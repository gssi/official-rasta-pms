package it.gssi.cs.rastapms.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "sensors_type")
public class SensorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "repetition")
    private int repetition;

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

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }
}
