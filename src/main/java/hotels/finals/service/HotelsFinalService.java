package hotels.finals.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hotels.finals.controller.model.HotelData;
import hotels.finals.controller.model.HotelData.CustomerData;
import hotels.finals.controller.model.HotelData.RoomData;
import hotels.finals.dao.CustomerDao;
import hotels.finals.dao.HotelDao;
import hotels.finals.dao.RoomDao;
import hotels.finals.entity.Customer;
import hotels.finals.entity.Hotel;
import hotels.finals.entity.Room;

@Service
public class HotelsFinalService {

    @Autowired
    private HotelDao hotelDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private CustomerDao customerDao;

    // Save or update hotel details
    @Transactional
    public HotelData saveHotel(HotelData hotelData) {
        Long hotelId = hotelData.getHotelId();
        Hotel hotel = findOrCreateHotel(hotelId);

        copyHotelFields(hotel, hotelData);
        return new HotelData(hotelDao.save(hotel));
    }

    private void copyHotelFields(Hotel hotel, HotelData hotelData) {
        hotel.setName(hotelData.getName());
        hotel.setAddress(hotelData.getAddress());
        hotel.setState(hotelData.getState());
    }

    // Save or update room details
    @Transactional
    public RoomData saveRoom(Long hotelId, RoomData roomData) {
        Hotel hotel = findHotelById(hotelId);
        Long roomId = roomData.getRoomId();
        Room room = findOrCreateRoom(roomId);       //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDORCREATEEMPLOYEE

        copyRoomFields(room, roomData);
        room.setHotel(hotel);
        hotel.getRooms().add(room);

        Room dbRoom = roomDao.save(room);
        return new RoomData(dbRoom);
    }

    private void copyRoomFields(Room room, RoomData roomData) {
        room.setRoomType(roomData.getRoomType());
        room.setBedrooms(roomData.getBedrooms());
        room.setView(roomData.getView());
    }

    // Save or update customer details
    @Transactional
    public CustomerData saveCustomer(Long hotelId, CustomerData customerData) {
        Hotel hotel = findHotelById(hotelId);
        Long customerId = customerData.getCustomerId();
        Customer customer = findOrCreateCustomer(customerId);   //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDORCREATECUSTOMER

        copyCustomerFields(customer, customerData);
        customer.getHotelNames().add(hotel);
        hotel.getCustomers().add(customer);

        Customer dbCustomer = customerDao.save(customer);
        return new CustomerData(dbCustomer);
    }

    private void copyCustomerFields(Customer customer, CustomerData customerData) {
        customer.setCustomerName(customerData.getCustomerName());
        customer.setCustomerEmail(customerData.getCustomerEmail());
        customer.setPassword(customerData.getPassword());
    }

    // Retrieve all hotels
    @Transactional(readOnly = true)
    public List<HotelData> retrieveAllHotels() {
        List<Hotel> hotels = hotelDao.findAll();
        List<HotelData> result = new LinkedList<>();

        for (Hotel hotel : hotels) {
            HotelData hfd = new HotelData(hotel);
            hfd.getRooms().clear();
            hfd.getHotelCustomers().clear();

            result.add(hfd);
        }

        return result;
    }

    // Retrieve a hotel by ID
    @Transactional(readOnly = true)
    public HotelData retrieveHotelById(Long hotelId) {
        return new HotelData(findHotelById(hotelId));
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

    private Room findOrCreateRoom(Long roomId) {            //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDORCREATEEMPLOYEE
        if (Objects.isNull(roomId)) {
            return new Room();
        }
        return findRoomById(roomId);
    }

    private Room findRoomById(Long roomId) {      //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDEMPLOYEEBYID
        return roomDao.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room with ID=" + roomId + " was not found."));
    }

    private Customer findOrCreateCustomer(Long customerId) {  //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDORCREATECUSTOMER
        if (Objects.isNull(customerId)) {
            return new Customer();
        }
        return findCustomerById(customerId);
    }

    private Customer findCustomerById(Long customerId) {   //THIS NEEDS TO INCLUDE HOTELID TO MATCH FINDCUSTOMERBYID
        return customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
    }
}