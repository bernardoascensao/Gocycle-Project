package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type; // Tipo de bicicleta: clássica ou elétrica
    private int weight; // Peso em gramas
    private String model;
    private String brand;
    private int numberOfGears; // Número de velocidades {1, 6, 18, 24}
    private String state; // Estado: livre, ocupado, em reserva, em manutenção
    private int autonomy; // Autonomia em km (apenas para bicicletas elétricas)
    private int maxSpeed; // Velocidade máxima em km/h (apenas para bicicletas elétricas)
    @OneToOne
    private GPSDevice gpsDevice; // Associação com dispositivo GPS
    private boolean isActive; // Indica se a bicicleta está ativa

    // Construtor
    public Bike(String type, int weight, String model, String brand, int numberOfGears,
                String state, int autonomy, int maxSpeed, GPSDevice gpsDevice) {
        this.type = type;
        this.weight = weight;
        this.model = model;
        this.brand = brand;
        this.numberOfGears = numberOfGears;
        this.state = state;
        this.autonomy = autonomy;
        this.maxSpeed = maxSpeed;
        this.gpsDevice = gpsDevice;
        this.isActive = true; // Inicialmente ativa
    }

    // Construtor sem argumentos (no-arg constructor) necessário para uso pelo mecanismo de persistência
    public Bike() {
        this.isActive = true; // Inicialmente ativa
    }

    // Getters e setters
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Outros métodos, se necessário
}
