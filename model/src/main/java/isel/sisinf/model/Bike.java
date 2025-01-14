package isel.sisinf.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Character type; //tipo de bicicleta: clássica ou elétrica
    private int weight;
    private String model;
    private String brand;
    private int numberOfGears; //número de velocidades {1, 6, 18, 24}
    private String state; //estado: livre, ocupado, em manutenção
    private int autonomy; //autonomia em km (apenas para bicicletas elétricas)
    private int maxSpeed; //velocidade máxima em km/h (apenas para bicicletas elétricas)
    private int GPSSerialNumber; //associação com dispositivo GPS
    private boolean isActive;

    // construtor
    public Bike(Character type, int weight, String model, String brand, int numberOfGears,
                String state, int autonomy, int maxSpeed, int gpsDevice) {
        if (type != 'E' && type != 'C') {
            throw new IllegalArgumentException("type must be 'C' or 'E'");
        }
        this.type = type;
        this.weight = weight;
        this.model = model;
        this.brand = brand;
        this.numberOfGears = numberOfGears;
        this.state = state;
        this.autonomy = autonomy;
        this.maxSpeed = maxSpeed;
        this.GPSSerialNumber = gpsDevice;
        this.isActive = true;       // inicialmente ativa
    }

    // construtor sem argumentos (no-arg constructor) necessário para uso pelo mecanismo de persistência
    public Bike() {
        this.isActive = true; // Inicialmente ativa
    }

    //getters e setters
    public boolean isActive() {
        return isActive;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isAvailable(EntityManager em, Date date) {
        List<Reservation> reservations = em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.bikeId = :bikeId AND r.startDate <= :date AND r.endDate >= :date AND r.isActive = true", Reservation.class)
                .setParameter("bikeId", this.id)
                .setParameter("date", date)
                .getResultList();
        return reservations.isEmpty();
    }

    public String getModel() {
        return model;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
