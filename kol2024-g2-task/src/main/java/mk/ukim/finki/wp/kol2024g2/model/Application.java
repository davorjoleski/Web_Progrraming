package mk.ukim.finki.wp.kol2024g2.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
public class Application {

    public Application() {
    }

    public Application(String name, String originStory, LocalDate dateCreated, ApplicationType applicationType, Scientist scientist) {
        this.name = name;
        this.originStory = originStory;
        this.dateCreated = dateCreated;
        this.applicationType = applicationType;
        this.scientist = scientist;
        this.votes = 0;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String originStory;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;
    @ManyToOne
    private Scientist scientist;

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

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
