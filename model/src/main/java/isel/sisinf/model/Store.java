package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "STORE")
@NamedQuery(
        name = "Store.findByKey",
        query = "SELECT s FROM Store s WHERE s.id = :key"
)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 40, nullable = false)
    private String locality;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager", referencedColumnName = "id")
    private Customer manager;

    @Column(nullable = false)
    private boolean isActive;

    public Store() {
        this.isActive = true;
    }

    public Store(Customer manager, String address, String locality, String phoneNumber, String email) {
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

    public Integer getId() {
        return id;
    }

    //getters e setters
}