package mk.ukim.finki.ecinema.config;


import mk.ukim.finki.ecinema.model.*;
import mk.ukim.finki.ecinema.model.enumerations.Role;
import mk.ukim.finki.ecinema.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {

    public static final String ADMIN = "admin";

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final DirectorService directorService;
    private final TicketService ticketService;
    private final SubscriptionService subscriptionService;
    private final DiscountService discountService;


    public DataInitializer(MovieService movieService, CategoryService categoryService, UserService userService, DirectorService directorService, TicketService ticketService, SubscriptionService subscriptionService, DiscountService discountService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.directorService = directorService;
        this.ticketService = ticketService;
        this.subscriptionService = subscriptionService;
        this.discountService = discountService;
    }


    @PostConstruct
    public void initData() {
        //Kategorii
        this.categoryService.create("Adventure");
        this.categoryService.create("Horror");
        this.categoryService.create("Drama");
        this.categoryService.create("Romance");
        this.categoryService.create("Comedy");
        this.categoryService.create("Action");
        List<Category> categories = this.categoryService.listAll();

        //Directors
        this.directorService.save("W. Bruce", "Cameron", "cameron@gmail.com");
        this.directorService.save("Peter", "Flinth", "peter@gmail.com");
        this.directorService.save("Emre", "Kabakusak", "emre@gmail.com");
        this.directorService.save("Rawson", "Thurber", "rawson@gmail.com");
        this.directorService.save("Antoine", "Fuqua", "antoine@gmail.com");
        this.directorService.save("Seth", "Gordon", "gordon@gmail.com");
        List<Director> directors = this.directorService.findAll();



        //movies
        this.movieService.create("A dog's way home", "A devoted, homesick dog goes on a treacherous journey across the American heartland to be reunited with her owner", directors.get(0),categories.get(0), LocalDateTime.now(), LocalDateTime.of(2022, 11, 25, 5, 30),null,"images/dog.jpg");
        this.movieService.create("Against the ice", "Exploring Greenland's vast landscape for a lost map, two men must fight to survive. Based on the true story of Denmark's 1909 polar expedition", directors.get(1),categories.get(2), LocalDateTime.now(), LocalDateTime.of(2023, 11, 25, 5, 30),null, "images/against.jpeg");
        this.movieService.create("Love Tactics", "An ad executive and a fashion designer-blogger don't believe in love, so they place a bet to make the other fall head over heels.", directors.get(2),categories.get(3), LocalDateTime.now(), LocalDateTime.of(2023, 11, 25, 5, 30),null, "images/love.jpg");
        this.movieService.create("Red Notice", "An FBI profiler pursuing the world's most wanted art thief becomes his reluctant partner in crime to catch an elusive crook who's always one step ahead", directors.get(3),categories.get(5), LocalDateTime.now(), LocalDateTime.of(2023, 11, 25, 5, 30),null, "images/red notice.jpg");
        this.movieService.create("The Equalizer 2", "Ex-CIA agent-turned-vigilante Robert McCall uses his deadly skills once again to avenge the death of a close friend and former colleague", directors.get(4),categories.get(4), LocalDateTime.now(), LocalDateTime.of(2023, 11, 25, 5, 30),null, "images/the eq.jpg");
        this.movieService.create("Baywatch", "To save their beach, elite lifeguard Mitch Buchannon and a former Olympian probe a criminal plot that threatens the future of the bay.", directors.get(5),categories.get(4), LocalDateTime.now(), LocalDateTime.of(2023, 11, 25, 5, 30),null, "images/baywatchh.jpg");

        List<Movie> movies = this.movieService.findAll();


        //Tickets
        this.ticketService.save(300.0, movies.get(0).getId());
        this.ticketService.save(400.0, movies.get(1).getId());
        this.ticketService.save(700.0, movies.get(2).getId());
        this.ticketService.save(200.0, movies.get(3).getId());
        this.ticketService.save(250.0, movies.get(4).getId());
        this.ticketService.save(400.0, movies.get(5).getId());
        List<Ticket> tickets = this.ticketService.findAll();


        //users
        this.userService.create("elena", "$2a$12$/aAEigl06Bg0p8pQael6vOHgZ1N8SsRcRHiHJbN2b.Ha/sbyaECnW", "elena", "babunska", Role.ROLE_USER);
        this.userService.create("viki", "$2a$12$VzfioHlnit7Nfd4knEk6Su/Wiz6UOIHnwCcrAuVA94htLf8XvrsTO", "viki", "ampova", Role.ROLE_ADMIN);

        //subscriptions
        this.subscriptionService.save("Monthly Plan", "Enjoy online cinema everywhere for a monthly price of 10$", 10.0, "https://hackup.co.uk/newsimage/1634564515monthly-subcription.png");
        this.subscriptionService.save("Annual Plan", "Enjoy online cinema everywhere for an annual price of 100$", 100.0, "https://roselightvideo.com/wp-content/uploads/2016/05/1-Year-Subscription.jpg");
        List<Subscription> subscriptions = this.subscriptionService.findAll();

        this.discountService.create(LocalDate.of(2022, 11, 25), "Discount 1", 50.0 );
    }

}


