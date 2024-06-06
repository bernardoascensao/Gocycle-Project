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
    private int serialNumber;
    private double latitude;
    private double longitude;
    private int batteryPercentage;
    private int bikeId;

    //construtor
    public GPSDevice(double latitude, double longitude, int batteryPercentage, int bikeId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryPercentage = batteryPercentage;
        this.bikeId = bikeId;
    }

    //construtor sem argumentos (necessário para o JPA)
    public GPSDevice() {
    }

    //getters e setters
    public int getSerialNumber() {
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

}
