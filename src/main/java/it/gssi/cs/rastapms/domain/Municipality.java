package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "municipality_id")
    private Long id;

    @JsonView(Views.POIPublic.class)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonView(Views.POIPublic.class)
    @Column(name = "zip_code", nullable = false)
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
