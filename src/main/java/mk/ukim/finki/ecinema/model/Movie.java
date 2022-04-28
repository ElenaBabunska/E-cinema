package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private Director director;

    @ManyToOne
    private Category category;

    private LocalDate premiereStart;

    private LocalDate premiereEnd;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    private String url;

    public Movie(){}

    public Movie(String name, String description, Director director, Category category, LocalDate premiereStart, LocalDate premiereEnd, List<Ticket> tickets, String url) {
        this.name = name;
        this.description = description;
        this.director = director;
        this.category = category;
        this.premiereStart = premiereStart;
        this.premiereEnd = premiereEnd;
        this.tickets = tickets;
        this.url=url;
    }
}
