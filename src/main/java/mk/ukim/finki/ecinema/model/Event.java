package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime start;

    @Column(name = "endTime")
    private LocalDateTime end;

    private String color;

    public Event(){}

    public Event(LocalDateTime start,LocalDateTime end,String text){
        this.start = start;
        this.end = end;
        this.text = text;
    }
}
