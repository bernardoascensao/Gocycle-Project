package isel.sisinf.jpa;

import isel.sisinf.model.Reservation;
import isel.sisinf.model.ReservationId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.LockModeType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservationRepository {

    private static final String DB_URL = "jdbc:postgresql://sisinfvlab2.dyn.fil.isel.pt:5432/t43dg36";
    private static final String USER = "t43dg36";
    private static final String PASS = "t43dg36";

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
    public void deleteReservationWithOptimisticLocking(ReservationId id) {
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Date parsedStartDate  = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            // Converte java.util.Date para java.sql.Timestamp
            Timestamp startDateTS = new Timestamp(parsedStartDate.getTime());
            Timestamp endDateTS = new Timestamp(parsedEndDate.getTime());

            String sql = "{call realizar_reserva(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, storeId);
            stmt.setInt(2, bikeId);
            stmt.setInt(3, customerId);
            stmt.setTimestamp(4, startDateTS);
            stmt.setTimestamp(5, endDateTS);
            stmt.setDouble(6, amount);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Fecha o EntityManagerFactory
    public void close() {
        emf.close();
    }
}
