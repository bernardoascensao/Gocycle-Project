package isel.sisinf.model;

import java.io.Serializable;
import java.util.Objects;

public class ReservationId implements Serializable {
    private int id;
    private int storeId;

    // Construtor sem argumentos (necessário para o JPA)
    public ReservationId() {}

    // Construtor com argumentos
    public ReservationId(int id, int storeId) {
        this.id = id;
        this.storeId = storeId;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
        return id == that.id && storeId == that.storeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeId);
    }
}
