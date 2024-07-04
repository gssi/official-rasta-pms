package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
@Table(name = "difficulties")
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficulty_id")
    private Long id;

    @JsonView(Views.ItineraryPublic.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.ItineraryPublic.class)
    @Column(name = "acronym")
    private String acronym;

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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
}
