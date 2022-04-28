package mk.ukim.finki.ecinema.service;


import mk.ukim.finki.ecinema.model.Event;
import java.time.LocalDateTime;
import java.util.List;


public interface EventService {
    Event create(LocalDateTime start, LocalDateTime end, String text);
    Event changeDate(Long id,LocalDateTime start,LocalDateTime end);
    Event putColor(Long id,String color);
    List<Event> findBetween(LocalDateTime start,LocalDateTime end);
}
