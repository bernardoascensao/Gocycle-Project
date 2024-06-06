package isel.sisinf.jpa;

import isel.sisinf.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.LockModeType;

import java.sql.*;
import java.util.List;

public class ReservationRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String USER = "myuser";
    private static final String PASS = "mypassword";

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("dal-lab");

    // Salva uma reserva
    public void saveReservation(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();
    }

    // Busca uma reserva pelo ID
    public Reservation getReservation(int id) {
        EntityManager em = emf.createEntityManager();
        Reservation reservation = em.find(Reservation.class, id);
        em.close();
        return reservation;
    }

    // Atualiza uma reserva
    public void updateReservation(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(reservation);
        em.getTransaction().commit();
        em.close();
    }


    // Deleta uma reserva logicamente com bloqueio otimista
    public void deleteReservationWithOptimisticLocking(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                em.lock(reservation, LockModeType.OPTIMISTIC);
                reservation.setActive(false); // Remoção lógica
                em.merge(reservation);
                em.getTransaction().commit();
            } else {
                System.out.println("Reserva não encontrada.");
            }
        } catch (OptimisticLockException e) {
            em.getTransaction().rollback();
            System.out.println("A reserva foi modificada por outro processo. Por favor, tente novamente.");
        } finally {
            em.close();
        }
    }

    // Busca todas as reservas ativas
    public List<Reservation> getAllReservations() {
        EntityManager em = emf.createEntityManager();
        List<Reservation> reservations = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
        em.close();
        return reservations;
    }

    // Salva uma reserva usando um procedimento armazenado
    public void saveReservationWithStoredProc(int storeId, int bikeId, int customerId, String startDate, String endDate, double amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            String sql = "{call realizar_reserva(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, storeId);
            stmt.setInt(2, bikeId);
            stmt.setInt(3, customerId);
            stmt.setTimestamp(4, Timestamp.valueOf(startDate));
            stmt.setTimestamp(5, Timestamp.valueOf(endDate));
            stmt.setDouble(6, amount);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fecha o EntityManagerFactory
    public void close() {
        emf.close();
    }
}
