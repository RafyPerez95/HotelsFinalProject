package hotels.finals.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import hotels.finals.controller.model.HotelsFinalData;
import hotels.finals.controller.model.HotelsFinalData.RoomData;
import hotels.finals.dao.CustomerDao;
import hotels.finals.dao.HotelNameDao;
import hotels.finals.dao.RoomDao;
import hotels.finals.entity.HotelName;
import hotels.finals.entity.Room;
@EntityScan

@Service
public class HotelsFinalService {

    @Autowired
    private HotelNameDao hotelDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private CustomerDao userDao;


    // Save or update hotel details
    @Transactional
    public HotelsFinalData saveHotel(HotelsFinalData hotelFinalData) {
        Long hotelId = hotelFinalData.getHotelId();
       HotelName hotelName = findOrCreateHotel(hotelId);

        copyHotelFields(hotel, hotelFinalData);
        return new HotelFinalData(hotelDao.save(hotel));
    }

    private void copyHotelFields(HotelName hotel, HotelFinalData hotelFinalData) {
        hotel.setName(hotelFinalData.getHotelName());
        hotel.setAddress(hotelFinalData.getHotelAddress());
        hotel.setState(hotelFinalData.getHotelState());
    }

    // Save or update room details
    @Transactional
    public HotelRoomData saveRoom(Long hotelId, HotelRoomData hotelRoomData) {
        HotelName hotel = findHotelById(hotelId);
        Long roomId = hotelRoomData.getRoomId();
        Room room = findOrCreateRoom(roomId);

        copyRoomFields(room, hotelRoomData);
        room.setHotel(hotel);
        hotel.getRooms().add(room);

        Room dbRoom = roomDao.save(room);
        return new HotelRoomData(dbRoom);
    }

    private void copyRoomFields(Room room, RoomData hotelRoomData) {
        room.setRoomType(hotelRoomData.getRoomType());
        room.setBedrooms(hotelRoomData.getBedrooms());
        room.setView(hotelRoomData.getView());
    }

    // Save or update user details
    @Transactional
    public HotelUserData saveUser(Long hotelId, HotelUserData hotelUserData) {
        HotelName hotel = findHotelById(hotelId);
        Long userId = hotelUserData.getUserId();
        User user = findOrCreateUser(userId);

        copyUserFields(user, hotelUserData);
        user.getHotels().add(hotel);
        hotel.getUsers().add(user);

        User dbUser = userDao.save(user);
        return new HotelUserData(dbUser);
    }

    private void copyUserFields(User user, HotelUserData hotelUserData) {
        user.setName(hotelUserData.getName());
        user.setEmail(hotelUserData.getEmail());
        user.setPassword(hotelUserData.getPassword());
    }

    // Save the relationship between hotel and user (hotel user mapping)
    @Transactional
    public HotelUser saveHotelUser(Long hotelId, Long userId) {
        Hotel hotel = findHotelById(hotelId);
        User user = findUserById(userId);

        HotelUser hotelUser = new HotelUser();
        hotelUser.setHotel(hotel);
        hotelUser.setUser(user);

        hotelUser = hotelUserDao.save(hotelUser);
        return hotelUser;
    }

    // Retrieve all hotels
    @Transactional(readOnly = true)
    public List<HotelsFinalData> retrieveAllHotels() {
        List<Hotel> hotels = hotelDao.findAll();
        List<HotelsFinalData> result = new LinkedList<>();

        for (Hotel hotel : hotels) {
            HotelsFinalData hfd = new HotelsFinalData(hotel);
            hfd.getRooms().clear();
            hfd.getUsers().clear();

            result.add(hfd);
        }

        return result;
    }

    // Retrieve a hotel by ID
    @Transactional(readOnly = true)
    public HotelsFinalData retrieveHotelById(Long hotelId) {
        return new HotelsFinalData(findHotelById(hotelId));
    }

    // Delete a hotel by ID
    @Transactional
    public void deleteHotelById(Long hotelId) {
        Hotel hotel = findHotelById(hotelId);
        hotelDao.delete(hotel);
    }

    // Private helper methods for entity lookups
    private Hotel findOrCreateHotel(Long hotelId) {
        if (Objects.isNull(hotelId)) {
            return new Hotel();
        }
        return findHotelById(hotelId);
    }

    private Hotel findHotelById(Long hotelId) {
        return hotelDao.findById(hotelId)
            .orElseThrow(() -> new NoSuchElementException("Hotel with ID=" + hotelId + " was not found."));
    }

    private Room findOrCreateRoom(Long roomId) {
        if (Objects.isNull(roomId)) {
            return new Room();
        }
        return findRoomById(roomId);
    }

    private Room findRoomById(Long roomId) {
        return roomDao.findById(roomId)
            .orElseThrow(() -> new NoSuchElementException("Room with ID=" + roomId + " was not found."));
    }

    private User findOrCreateUser(Long userId) {
        if (Objects.isNull(userId)) {
            return new User();
        }
        return findUserById(userId);
    }

    private User findUserById(Long userId) {
        return userDao.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User with ID=" + userId + " was not found."));
    }
}