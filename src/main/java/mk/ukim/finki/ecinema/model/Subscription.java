package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    Double price;
    String url;
    LocalDateTime start;
    LocalDateTime end;

    @OneToMany(mappedBy = "subscription",cascade = CascadeType.ALL)
    List<User> users;

    public Subscription(){

    }

    public Subscription(String name, String description, Double price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
        this.users=new ArrayList<>();
        this.start = LocalDateTime.now();
        this.end = LocalDateTime.now();
    }
}
