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
    private String code; // Código único da loja
    private String manager; // Gestor da loja
    private String address; // Morada da loja
    private String locality; // Localidade
    private String phoneNumber; // Número de telefone
    private String email; // Endereço eletrônico
    private boolean isActive; // Indica se a loja está ativa

    public Store() {
        this.isActive = true;
    }

    public Store(String code, String manager, String address, String locality, String phoneNumber, String email) {
        this.code = code;
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

    // Getters e setters
}