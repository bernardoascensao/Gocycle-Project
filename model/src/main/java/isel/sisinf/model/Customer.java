package isel.sisinf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idNumber;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String ccNumber;
    private String nationality;

    public Customer(String name, String address, String email, String phone, String ccNumber, String nationality) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.ccNumber = ccNumber;
        this.nationality = nationality;
    }

    // Construtor sem argumentos (necessário para o JPA)
    public Customer() {
    }

    // Getters and Setters
    public String getId() {
        return idNumber;
    }

    public void setId(String id) {
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
}
