package hotels.finals.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import hotels.finals.entity.HotelName;

public interface HotelNameDao extends JpaRepository<HotelName, Long> {

}
