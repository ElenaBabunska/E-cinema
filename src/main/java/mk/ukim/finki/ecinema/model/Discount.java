package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateCreated;
    private LocalDate validUntil;
    private Double value;

    @ManyToMany
    private List<User> users;

    public Discount(){}

    public Discount(LocalDate validUntil, String name, Double value){
        this.dateCreated = LocalDate.now();
        this.validUntil = validUntil;
        this.users = new ArrayList<>();
        this.name = name;
        this.value = value;
    }
}
