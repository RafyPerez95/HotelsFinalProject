package hotels.finals.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import hotels.finals.entity.Room;

public interface RoomDao extends JpaRepository<Room, Long> {

}
