package isel.sisinf.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BIKE")
@NamedQueries({
    @NamedQuery(name = "Bike.findByKey", query = "SELECT b FROM Bike b WHERE b.id = :key"),
    @NamedQuery(name = "Bike.findAll", query = "SELECT b FROM Bike b"),
})
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 1)
    private char type; //tipo de bicicleta: clássica ou elétrica

    @Column(nullable = false, precision = 4, scale = 2)
    private int weight;

    @Column(nullable = false, length = 20)
    private String model;

    @Column(nullable = false, length = 30)
    private String brand;

    @Column
    private int numberOfGears; //número de velocidades {1, 6, 18, 24}

    @Column(nullable = false, length = 20)
    private String state; //state: free, busy, maintenance

    @Column(nullable = false)
    private int autonomy;

    @Column(nullable = false)
    private int maxSpeed;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "GPSSerialNumber", referencedColumnName = "serialNumber")
    private GPSDevice GPSSerialNumber;

    @Column(nullable = false)
    private boolean isActive;

    // construtor
    public Bike(Character type, int weight, String model, String brand, int numberOfGears,
                String state, int autonomy, int maxSpeed, GPSDevice gpsDevice) {
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
        this.isActive = true;
    }

    // construtor sem argumentos (no-arg constructor) necessário para uso pelo mecanismo de persistência
    public Bike() {
        this.isActive = true;
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

    public String getModel() {
        return model;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Character getType() { return type; }
}
