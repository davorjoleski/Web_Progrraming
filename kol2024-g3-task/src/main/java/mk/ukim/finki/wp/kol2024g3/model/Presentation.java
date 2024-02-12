package mk.ukim.finki.wp.kol2024g3.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
public class Presentation {

    public Presentation() {
    }

    public Presentation(String name, String description, LocalDate datePresented, PresentationType presentationType, Scientist scientist) {
        this.name = name;
        this.description = description;
        this.datePresented = datePresented;
        this.presentationType = presentationType;
        this.scientist = scientist;
        this.votes = 0;
    }
@Id
@GeneratedValue
    private Long id;

    private String name;

    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDate datePresented;
    @Enumerated(EnumType.STRING)
    private PresentationType presentationType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDatePresented() {
        return datePresented;
    }

    public void setDatePresented(LocalDate datePresented) {
        this.datePresented = datePresented;
    }

    public PresentationType getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(PresentationType presentationType) {
        this.presentationType = presentationType;
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
