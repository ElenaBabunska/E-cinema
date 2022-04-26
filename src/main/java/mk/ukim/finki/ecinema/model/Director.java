package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long embg;

    private String name;

    private String surname;

    private String eMail;

    public Director(){}

    public Director(String name, String surname, String eMail) {
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
    }
}
