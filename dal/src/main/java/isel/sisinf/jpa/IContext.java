package isel.sisinf.jpa;

import isel.sisinf.jpa.Bike.IBikeRepository;
import isel.sisinf.jpa.Customer.ICustomerRepository;
import isel.sisinf.jpa.Reservation.IReservationRepository;
import isel.sisinf.jpa.Store.IStoreRepository;

public interface IContext extends AutoCloseable {

    enum IsolationLevel {READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE }
    void beginTransaction();
    void beginTransaction(IsolationLevel isolationLevel);
    void commit();
    void flush();
    void clear();
    void persist(Object entity);

    ICustomerRepository getCustomerRepo();
    IBikeRepository getBikeRepo();
    IReservationRepository getReservationRepo();
    IStoreRepository getStoreRepo();
}
