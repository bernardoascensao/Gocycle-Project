package isel.sisinf.jpa;

import jakarta.persistence.EntityManager;
import isel.sisinf.model.Customer;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CustomerRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("dal-lab");

    public void saveCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
    }

    public Customer getCustomer(String idNumber) {
        EntityManager em = emf.createEntityManager();
        Customer customer = em.find(Customer.class, idNumber);
        em.close();
        return customer;
    }

    public void updateCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(customer);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCustomer(String idNumber) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, idNumber);
        if (customer != null){
            em.remove(customer);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void close() {
        emf.close();
    }
}
