package mk.ukim.finki.wp.kol2024g1.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
public class Song {

    public Song() {
    }

    public Song(String name, String originStory, LocalDate dateCreated, Genre genre, Artist artist) {
        this.name = name;
        this.originStory = originStory;
        this.dateCreated = dateCreated;
        this.genre = genre;
        this.artist = artist;
        this.votes = 0;
    }


    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    private String originStory;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne
    private Artist artist;

    private Integer votes = 0;

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

    public String getOriginStory() {
        return originStory;
    }

    public void setOriginStory(String originStory) {
        this.originStory = originStory;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
