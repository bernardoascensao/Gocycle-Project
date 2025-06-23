package isel.sisinf.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
@NamedQueries({
    @NamedQuery(
            name = "Reservation.findByKey",
            query = "SELECT r FROM Reservation r WHERE r.id = :key"
    ),
    @NamedQuery(
            name = "Reservation.findAll",
            query = "SELECT r FROM Reservation r"
    )
})
public class Reservation {
    @EmbeddedId
    private ReservationId id;

    @MapsId("storeId")
    @ManyToOne
    @JoinColumn(name = "store", referencedColumnName = "id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "bike", nullable = false, referencedColumnName = "id")
    private Bike bike;

    @ManyToOne
    @JoinColumn(name = "customer", nullable = false, referencedColumnName = "id")
    private Customer customer;

    @Column(nullable = false)
    private Timestamp startDate;
    @Column(nullable = false)
    private Timestamp endDate;

    @Column(nullable = false, precision = 5, scale = 2)
    private double amount;

    @Version
    private int version; // Campo de versão para controle de concorrência

    //construtor sem argumentos (necessário para o JPA)
    public Reservation() {}

    //construtor
    public Reservation(Store store, Bike bike, Customer customer, String startDateStr, String endDateStr, double amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date parsedStartDate  = dateFormat.parse(startDateStr);
            Date parsedEndDate  = dateFormat.parse(endDateStr);

            // Converte java.util.Date para java.sql.Timestamp
            Timestamp startDate = new Timestamp(parsedStartDate.getTime());
            Timestamp endDate = new Timestamp(parsedEndDate.getTime());

            this.store = store;
            this.bike = bike;
            this.customer = customer;
            this.startDate = startDate;
            this.endDate = endDate;
            this.amount = amount;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format", e);
        }
    }

    // getters & setters
    public ReservationId getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public Double getAmount() {
        return amount;
    }
}
