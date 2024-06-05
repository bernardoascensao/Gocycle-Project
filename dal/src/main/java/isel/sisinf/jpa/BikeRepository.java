package isel.sisinf.jpa;

import isel.sisinf.model.Bike;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BikeRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");

    public void saveBike(Bike bike) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(bike);
        em.getTransaction().commit();
        em.close();
    }

    public Bike getBike(int id) {
        EntityManager em = emf.createEntityManager();
        Bike bike = em.find(Bike.class, id);
        em.close();
        return bike;
    }

    public void updateBike(Bike bike) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(bike);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteBike(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Bike bike = em.find(Bike.class, id);
        if (bike != null) {
            bike.setActive(false); // Remoção lógica
            em.merge(bike);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Bike> getAllBikes() {
        EntityManager em = emf.createEntityManager();
        List<Bike> bikes = em.createQuery("SELECT b FROM Bike b", Bike.class).getResultList();
        em.close();
        return bikes;
    }

    public List<Bike> getAllActiveBikes() {
        EntityManager em = emf.createEntityManager();
        List<Bike> bikes = em.createQuery("SELECT b FROM Bike b WHERE b.isActive = true", Bike.class).getResultList();
        em.close();
        return bikes;
    }

    public boolean checkBikeAvailability(String id, String date) {
        try (EntityManager em = emf.createEntityManager()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date checkDate = dateFormat.parse(date);
            List<Bike> bikes = em.createQuery("SELECT b FROM Bike b WHERE b.id = :id AND b.isActive = true", Bike.class)
                    .setParameter("id", Integer.parseInt(id))
                    .getResultList();
            if (bikes.isEmpty()) {
                return false;
            }
            Bike bike = bikes.get(0);
            return bike.isAvailable(em, checkDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        emf.close();
    }
}
