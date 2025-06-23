package isel.sisinf.jpa.Store;

import isel.sisinf.jpa.IDataMapper;
import isel.sisinf.jpa.IRepository;
import isel.sisinf.model.Store;
import java.util.Collection;

public interface IStoreRepository extends IRepository<Store, Collection<Store>, Integer>, IDataMapper<Store> {

}
