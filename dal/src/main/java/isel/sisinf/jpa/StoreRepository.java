package isel.sisinf.jpa;

import isel.sisinf.model.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class StoreRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");

    public void saveStore(Store store) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(store);
        em.getTransaction().commit();
        em.close();
    }

    public Store getStore(int id) {
        EntityManager em = emf.createEntityManager();
        Store store = em.find(Store.class, id);
        em.close();
        return store;
    }

    public void updateStore(Store store) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(store);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteStore(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Store store = em.find(Store.class, id);
        if (store != null) {
            store.setActive(false); // Remoção lógica
            em.merge(store);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Store> getAllStores() {
        EntityManager em = emf.createEntityManager();
        List<Store> stores = em.createQuery("SELECT s FROM Store s WHERE s.isActive = true", Store.class).getResultList();
        em.close();
        return stores;
    }

    public void close() {
        emf.close();
    }
}
