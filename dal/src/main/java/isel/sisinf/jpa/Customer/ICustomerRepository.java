package isel.sisinf.jpa.Customer;

import isel.sisinf.jpa.IDataMapper;
import isel.sisinf.jpa.IRepository;
import isel.sisinf.model.Customer;
import java.util.Collection;

public interface ICustomerRepository extends IRepository<Customer, Collection<Customer>, Integer>, IDataMapper<Customer> {

}
