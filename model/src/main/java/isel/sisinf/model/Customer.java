package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(
        name = "Customer.findByKey",
        query = "SELECT c FROM Customer c WHERE c.id = :key"
)
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 30, unique = true)
    private String phone;

    @Column(length = 12, unique = true, nullable = false)
    private String ccNumber;

    @Column(length = 20, nullable = false)
    private String nationality;

    @Column(length = 1, nullable = false)
    private char atrdisc;

    @Column(nullable = false)
    private Boolean isActive; // Campo para remoção lógica

    public Customer(String name, String address, String email, String phone, String ccNumber, String nationality, Character atrdisc) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.ccNumber = ccNumber;
        this.nationality = nationality;
        if (atrdisc != 'C' && atrdisc != 'G') {
            throw new IllegalArgumentException("atrdisc must be 'C' or 'G'");
        }
        this.atrdisc = atrdisc;
        this.isActive = true; // Cliente inicialmente ativo
    }

    //construtor sem argumentos (necessário para o JPA)
    public Customer() {
        this.isActive = true; // Cliente inicialmente ativo
    }
    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
