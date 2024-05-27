package isel.sisinf.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int storeId; // ID da loja (relacionamento com Store)
    private int bikeId; // ID da bicicleta (relacionamento com Bike)
    private String customerId; // ID do cliente (relacionamento com Customer)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate; // Data de início da reserva
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate; // Data de fim da reserva
    private double amount; // Valor a pagar
    private boolean isActive; // Indica se a reserva está ativa

    public Reservation() {}

    public Reservation(int storeId, int bikeId, String customerId, Date startDate, Date endDate, double amount) {
        this.storeId = storeId;
        this.bikeId = bikeId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.isActive = true;
    }

    // Getters e setters
}
