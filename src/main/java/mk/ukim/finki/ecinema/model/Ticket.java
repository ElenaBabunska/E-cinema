package mk.ukim.finki.ecinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private Double price;

    private Double discountPrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Discount discount;

    @ManyToOne
    private Movie movie;

    public Ticket(){}

    public Ticket(Double price, Movie movie) {
        this.price = price;
        this.movie = movie;
        this.discountPrice=price;
    }

    public Ticket(Double price, Double discountPrice){
        this.price = price;
        this.discountPrice = discountPrice;
    }


}
