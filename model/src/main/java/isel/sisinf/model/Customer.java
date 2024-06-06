package isel.sisinf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNumber;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String ccNumber;
    private String nationality;
    private Character atrdisc;
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
        return idNumber;
    }

    public void setId(int id) {
        this.idNumber = id;
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
