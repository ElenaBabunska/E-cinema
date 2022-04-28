package mk.ukim.finki.ecinema.web.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import mk.ukim.finki.ecinema.model.Event;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.service.EventService;
import mk.ukim.finki.ecinema.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class EventsRestController {


    private final EventService eventService;
    private final UserService userService;

    public EventsRestController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    @RequestMapping("/events")
    public ModelAndView home(HttpServletRequest req) {
//        String username  = req.getRemoteUser();
//        User user = this.userService.findByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject(user);
        modelAndView.getModel().put("bodyContent","calendar.html");
        modelAndView.setViewName("master-template");
        return modelAndView;
    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
                           @RequestParam("end")
                           @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return eventService.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event createEvent(@RequestBody EventCreateParams params) {
        return eventService.create(params.start,params.end, params.text);
    }

    @PostMapping("/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event moveEvent(@RequestBody EventMoveParams params) {
        return eventService.changeDate(params.id,params.start,params.end);
    }

    @PostMapping("/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event setColor(@RequestBody SetColorParams params) {
        return eventService.putColor(params.id,params.color);
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
        public Long resource;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
        public Long resource;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }


}