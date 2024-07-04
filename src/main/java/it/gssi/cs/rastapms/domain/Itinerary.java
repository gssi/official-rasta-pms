package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "itineraries")
public class Itinerary {
    @JsonView({Views.ItineraryPublic.class, Views.ItineraryPrivate.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Long id;

    @JsonView({Views.ItineraryPublic.class, Views.ItineraryPrivate.class})
    @Column(name = "code", nullable = false)
    @NotBlank(message = "code is mandatory")
    @Size(min = 1, max = 255)
    private String code;

    @JsonView({Views.ItineraryPublic.class, Views.ItineraryPrivate.class})
    @Column(name = "name", nullable = false)
    @NotBlank(message = "name is mandatory")
    @Size(min = 1, max = 255)
    private String name;

    @JsonView(Views.ItineraryPublic.class)
    @Lob
    @Column(name="description", length=4096, nullable = false)
    @NotBlank(message = "description is mandatory")
    @Size(min = 1, max = 4096)
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "poi_start_id")
    private ItineraryPoint start;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "poi_end_id")
    private ItineraryPoint end;

    @JsonView(Views.ItineraryPublic.class)
    @Column(name = "itinerary_time", nullable = false)
    @NotNull(message = "itineraryTime is mandatory")
    private LocalTime itineraryTime;

    @JsonView(Views.ItineraryPublic.class)
    @Column(name = "length", nullable = false)
    @NotNull(message = "length is mandatory")
    private float length;

    @JsonView(Views.ItineraryPublic.class)
    @ManyToOne
    @JoinColumn(name = "difficulty_id", nullable = false)
    @NotNull(message = "difficulty is mandatory")
    private Difficulty difficulty;

    @JsonView(Views.ItineraryPublic.class)
    @ManyToOne
    @JoinColumn(name = "itinerary_state_id", nullable = false)
    @NotNull(message = "state is mandatory")
    private ItineraryState state;

    @JsonView(Views.ItineraryPublic.class)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "itineraries_categories", joinColumns = {@JoinColumn(name = "itinerary_id")}, inverseJoinColumns = {
            @JoinColumn(name = "itinerary_category_id")})
    @NotEmpty(message = "categories cannot be empty")
    private Set<ItineraryCategory> categories = new HashSet<>();

    @JsonView(Views.ItineraryPublic.class)
    @Column(name="map", columnDefinition = "longtext", length = 4096)
    @NotBlank(message = "map is mandatory")
    private String map;

    @Column(name="image", columnDefinition = "longblob")
    @Lob
    private byte[] image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ItineraryPoint getStart() {
        return start;
    }

    public void setStart(ItineraryPoint start) {
        this.start = start;
    }

    public ItineraryPoint getEnd() {
        return end;
    }

    public void setEnd(ItineraryPoint end) {
        this.end = end;
    }

    public LocalTime getItineraryTime() {
        return itineraryTime;
    }

    public void setItineraryTime(LocalTime itineraryTime) {
        this.itineraryTime = itineraryTime;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ItineraryState getState() {
        return state;
    }

    public void setState(ItineraryState state) {
        this.state = state;
    }

    public Set<ItineraryCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ItineraryCategory> categories) {
        this.categories = categories;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
