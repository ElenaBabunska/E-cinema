package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Movie> movies;

    public Favourite() {
    }

    public Favourite(User user) {
        this.user = user;
        this.movies = new ArrayList<>();
    }
}
