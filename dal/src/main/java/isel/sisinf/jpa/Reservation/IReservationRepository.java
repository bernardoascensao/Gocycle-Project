package isel.sisinf.jpa.Reservation;

import isel.sisinf.jpa.IDataMapper;
import isel.sisinf.jpa.IRepository;
import isel.sisinf.model.Reservation;
import isel.sisinf.model.ReservationId;
import java.util.Collection;

public interface IReservationRepository extends IRepository<Reservation, Collection<Reservation>, ReservationId>, IDataMapper<Reservation> {
    Collection<Reservation> findAll();
}
