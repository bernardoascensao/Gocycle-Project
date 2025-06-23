package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
public class GPSDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serialNumber;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private int batteryPercentage;

    //construtor
    public GPSDevice(double latitude, double longitude, int batteryPercentage, int bikeId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryPercentage = batteryPercentage;
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
