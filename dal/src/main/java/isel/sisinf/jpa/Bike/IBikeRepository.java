package isel.sisinf.jpa.Bike;

import isel.sisinf.jpa.IDataMapper;
import isel.sisinf.jpa.IRepository;
import isel.sisinf.model.Bike;
import java.sql.Timestamp;
import java.util.Collection;

public interface IBikeRepository extends IRepository<Bike, Collection<Bike>, Integer>, IDataMapper<Bike> {
    Collection<Bike> findAll();
    boolean checkBikeAvailability(int bikeId, Timestamp dateTime);
}

