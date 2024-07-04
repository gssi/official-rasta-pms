package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "point_of_interests")
public class POI {
    @JsonView({Views.POIPublic.class, Views.POIPrivate.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poi_id")
    private Long id;

    @JsonView({Views.POIPublic.class, Views.POIPrivate.class})
    @Column(name = "name", unique = true, nullable = false)
    @NotBlank(message = "name is mandatory")
    @Size(min = 1, max = 255)
    private String name;

    @JsonView({Views.POIPublic.class, Views.POIPrivate.class})
    @Lob
    @Column(name="description", length=4096)
    @NotBlank(message = "description is mandatory")
    @Size(min = 1, max = 4096)
    private String description;

    @JsonView(Views.POIPublic.class)
    @Column(name = "latitude", nullable = false)
    @NotNull(message = "latitude is mandatory")
    private double latitude;

    @JsonView(Views.POIPublic.class)
    @Column(name = "longitude", nullable = false)
    @NotNull(message = "longitude is mandatory")
    private double longitude;

    @JsonView(Views.POIPublic.class)
    @Column(name = "altitude")
    private Long altitude;

    @JsonView(Views.POIPublic.class)
    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    @NotNull(message = "municipality is mandatory")
    private Municipality municipality;

    @JsonView(Views.POIPublic.class)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "pois_categories", joinColumns = {@JoinColumn(name = "poi_id")}, inverseJoinColumns = {
            @JoinColumn(name = "category_id")})
    @NotEmpty(message = "categories cannot be empty")
    private Set<POICategory> categories = new HashSet<>();

    @JsonView(Views.POIPublic.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "image_id", nullable = false)
    @NotNull(message = "primaryImage is mandatory")
    private Image primaryImage;

    @JsonIgnore
    @OneToMany(mappedBy = "poi", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    public void addImage(Image image) {
        image.setPoi(this);
        images.add(image);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getAltitude() {
        return altitude;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public Set<POICategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<POICategory> categories) {
        this.categories = categories;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Image getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(Image primaryImage) {
        this.primaryImage = primaryImage;
    }
}
