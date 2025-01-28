package hotels.finals.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import hotels.finals.entity.Customer;



public interface CustomerDao extends JpaRepository<Customer, Long> {


}
