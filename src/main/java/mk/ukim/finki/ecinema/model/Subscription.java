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
    LocalDateTime start;
    LocalDateTime end;

    @OneToMany(mappedBy = "subscription",cascade = CascadeType.ALL)
    List<User> users;

    public Subscription(){

    }

    public Subscription(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.users=new ArrayList<>();
        this.start = LocalDateTime.now();
        this.end = LocalDateTime.now();
    }
}
