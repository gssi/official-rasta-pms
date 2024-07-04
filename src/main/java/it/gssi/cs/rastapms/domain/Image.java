package it.gssi.cs.rastapms.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @JsonView(Views.POIPublic.class)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author")
    private String author;

    @JsonView(Views.POIPublic.class)
    @Column(name="content", columnDefinition = "longblob")
    @Lob
    private byte[] content;

    @JsonView(Views.POIPublic.class)
    @Column(name = "image_type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "poi_id", nullable = true)
    private POI poi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public POI getPoi() {
        return poi;
    }

    public void setPoi(POI poi) {
        this.poi = poi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
