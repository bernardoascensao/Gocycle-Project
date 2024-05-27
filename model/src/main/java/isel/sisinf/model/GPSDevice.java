package isel.sisinf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class GPSDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String serialNumber;
    private double latitude;
    private double longitude;
    private int batteryPercentage;

    @OneToOne(mappedBy = "gpsDevice")
    private Bike bike;

    // Construtor completo
    public GPSDevice(double latitude, double longitude, int batteryPercentage) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryPercentage = batteryPercentage;
    }

    // Construtor sem argumentos (necessário para o JPA)
    public GPSDevice() {
    }

    // Getters e Setters

    public String getSerialNumber() {
        return serialNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
