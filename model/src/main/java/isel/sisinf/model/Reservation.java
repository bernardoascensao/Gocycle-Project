package isel.sisinf.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@IdClass(ReservationId.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    private int storeId;
    private int bikeId;
    private int customerId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;         //data de início da reserva
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;           //data de fim da reserva
    private double amount;
    private boolean isActive;       //?? perguntar se é boa ideia ter este campo

    //construtor sem argumentos (necessário para o JPA)
    public Reservation() {}

    //construtor
    public Reservation(int storeId, int bikeId, int customerId, String startDateStr, String endDateStr, double amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            this.storeId = storeId;
            this.bikeId = bikeId;
            this.customerId = customerId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.amount = amount;
            this.isActive = true;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format", e);
        }
    }

    //getters e setters
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(startDate);
    }

    public String getEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(endDate);
    }

    public String getAmount() {
        return String.format("%.2f", amount);
    }
}
