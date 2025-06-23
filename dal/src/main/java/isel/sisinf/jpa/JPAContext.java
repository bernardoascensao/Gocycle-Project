package isel.sisinf.jpa;

import isel.sisinf.jpa.Bike.IBikeRepository;
import isel.sisinf.jpa.Customer.ICustomerRepository;
import isel.sisinf.jpa.Reservation.IReservationRepository;
import isel.sisinf.jpa.Store.IStoreRepository;
import isel.sisinf.model.*;
import jakarta.persistence.*;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public class JPAContext implements IContext {

    private EntityManagerFactory _emf;
    private EntityManager _em;
    private EntityTransaction _tx;
    private int _txcount;

    private final ICustomerRepository _customerRepository;
    private final IBikeRepository _bikeRepository;
    private final IReservationRepository _reservationRepository;
    private final IStoreRepository _storeRepository;


    // HELPER METHODS
    protected Collection helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }

    protected Object helperCreateImpl(Object entity) {
        beginTransaction();
        _em.persist(entity);
        commit();
        return entity;
    }

    protected Object helperUpdateImpl(Object entity) {
        beginTransaction();
        _em.merge(entity);
        commit();
        return entity;
    }

    protected Object helperDeleteImpl(Object entity) {
        beginTransaction();
        _em.remove(entity);
        commit();
        return entity;
    }


    // REPOSITORIES
    protected class CustomerRepository implements ICustomerRepository {
        @Override
        public Customer create(Customer entity) {
            return (Customer) helperCreateImpl(entity);
        }

        @Override
        public Customer update(Customer entity) {
            return (Customer) helperUpdateImpl(entity);
        }

        @Override
        public Customer delete(Customer entity) {
            return (Customer) helperDeleteImpl(entity);
        }

        @Override
        public Customer findByKey(Integer key) {
            return _em.createNamedQuery("Customer.findByKey", Customer.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Customer> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }
    }

    protected class BikeRepository implements IBikeRepository {
        @Override
        public Bike create(Bike entity) {
            return (Bike) helperCreateImpl(entity);
        }

        @Override
        public Bike update(Bike entity) {
            return (Bike) helperUpdateImpl(entity);
        }

        @Override
        public Bike delete(Bike entity) {
            return (Bike) helperDeleteImpl(entity);
        }

        @Override
        public Bike findByKey(Integer key) {
            return _em.createNamedQuery("Bike.findByKey", Bike.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Bike> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Collection<Bike> findAll() {
            return _em.createNamedQuery("Bike.findAll", Bike.class).getResultList();
        }

        @Override
        public boolean checkBikeAvailability(int bikeId, Timestamp dateTime) {
            Query query = _em.createNativeQuery("SELECT check_bike_availability(?, ?)");
            query.setParameter(1, bikeId);
            query.setParameter(2, dateTime);

            Boolean result = (Boolean) query.getSingleResult();
            return result != null && result;
        }
    }

    protected class ReservationRepository implements IReservationRepository {
        @Override
        public Reservation create(Reservation entity) {
            return (Reservation) helperCreateImpl(entity);
        }

        @Override
        public Reservation update(Reservation entity) {
            return (Reservation) helperUpdateImpl(entity);
        }

        @Override
        public Reservation delete(Reservation entity) {
            return (Reservation) helperDeleteImpl(entity);
        }

        @Override
        public Reservation findByKey(ReservationId key) {
            return _em.createNamedQuery("Reservation.findByKey", Reservation.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Reservation> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Collection<Reservation> findAll() {
            return _em.createNamedQuery("Reservation.findAll", Reservation.class).getResultList();
        }
    }

    protected class StoreRepository implements IStoreRepository {
        @Override
        public Store create(Store entity) {
            return (Store) helperCreateImpl(entity);
        }

        @Override
        public Store update(Store entity) {
            return (Store) helperUpdateImpl(entity);
        }

        @Override
        public Store delete(Store entity) {
            return (Store) helperDeleteImpl(entity);
        }

        @Override
        public Store findByKey(Integer key) {
            return _em.createNamedQuery("Store.findByKey", Store.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Store> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }
    }

    @Override
    public ICustomerRepository getCustomerRepo() {
        return this._customerRepository;
    }

    @Override
    public IBikeRepository getBikeRepo() {
        return this._bikeRepository;
    }

    @Override
    public IReservationRepository getReservationRepo() {
        return this._reservationRepository;
    }

    @Override
    public IStoreRepository getStoreRepo() {
        return this._storeRepository;
    }


    // Constructor
    public JPAContext() {
        this("dal-lab");
    }

    public JPAContext(String persistentCtx) {
        super();
        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._customerRepository = new CustomerRepository();
        this._bikeRepository = new BikeRepository();
        this._reservationRepository = new ReservationRepository();
        this._storeRepository = new StoreRepository();
    }


    @Override
    public void beginTransaction() {
        if(_tx == null)
        {
            _tx = _em.getTransaction();
            _tx.begin();
            _txcount=0;
        }
        ++_txcount;
    }

    @Override
    public void beginTransaction(IsolationLevel isolationLevel) {
        beginTransaction();
        Session session =_em.unwrap(Session.class);
        DatabaseLogin databaseLogin = (DatabaseLogin) session.getDatasourceLogin();
        System.out.println(databaseLogin.getTransactionIsolation());

        int isolation = DatabaseLogin.TRANSACTION_READ_COMMITTED;
        if(isolationLevel == IsolationLevel.READ_UNCOMMITTED)
            isolation = DatabaseLogin.TRANSACTION_READ_UNCOMMITTED;
        else if(isolationLevel == IsolationLevel.REPEATABLE_READ)
            isolation = DatabaseLogin.TRANSACTION_REPEATABLE_READ;
        else if(isolationLevel == IsolationLevel.SERIALIZABLE)
            isolation = DatabaseLogin.TRANSACTION_SERIALIZABLE;

        databaseLogin.setTransactionIsolation(isolation);
    }

    @Override
    public void commit() {
        try {
            --_txcount;
            if(_txcount==0 && _tx != null)
            {
                _em.flush();
                _tx.commit();
                _tx = null;
            }
        } catch (Exception e) {
            _tx.rollback();
            throw e;
        }
    }

    @Override
    public void flush() {
        _em.flush();
    }

    @Override
    public void clear() {
        _em.clear();
    }

    @Override
    public void persist(Object entity) {
        _em.persist(entity);
    }

    @Override
    public void close() throws Exception {
        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }
}
