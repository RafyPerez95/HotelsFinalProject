package hotels.finals.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import hotels.finals.entity.Hotel;

public interface HotelDao extends JpaRepository<Hotel, Long> {

}
