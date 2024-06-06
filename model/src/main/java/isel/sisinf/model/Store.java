package isel.sisinf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int manager;
    private String address;
    private String locality;
    private String phoneNumber;
    private String email;
    private boolean isActive; //indica se a loja está ativa

    public Store() {
        this.isActive = true;
    }

    public Store(int manager, String address, String locality, String phoneNumber, String email) {
        this.manager = manager;
        this.address = address;
        this.locality = locality;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    //getters e setters
}