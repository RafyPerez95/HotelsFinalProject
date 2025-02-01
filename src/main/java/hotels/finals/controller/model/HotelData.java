package hotels.finals.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import hotels.finals.entity.Room;
import hotels.finals.entity.Customer;
import hotels.finals.entity.Hotel;


@Data
@NoArgsConstructor
public class HotelData {

    private Long hotelId;
    private String name; 
    private String address;
    private String state;
    private Set<RoomData> rooms = new HashSet<>();
    private Set<CustomerData> hotelCustomers = new HashSet<>();  

    
    public HotelData(Hotel hotel) {
        hotelId = hotel.getHotelId();
        name = hotel.getName();  
        address = hotel.getAddress();  
        state = hotel.getState();  
        
        // Mapping rooms for this hotel
        for (Room room : hotel.getRooms()) {
            rooms.add(new RoomData(room));
        }


        // Mapping hotel customers (Customer - Hotel relationship)
        for (Customer customer : hotel.getCustomers()) {
            hotelCustomers.add(new CustomerData(customer));
        }
    }

    
    @Data
    @NoArgsConstructor
    public static class RoomData {
        private Long roomId;
        private String roomType;
        private Integer bedrooms;
        private String view;

        public RoomData(Room room) {
            roomId = room.getRoomId();
            roomType = room.getRoomType();
            bedrooms = room.getBedrooms();
            view = room.getView();
        }
    }

   
    @Data
    @NoArgsConstructor
    public static class CustomerData {
        private Long customerId;
        private String customerName;
        private String customerEmail;
        private String password; 

        public CustomerData(Customer customer) {
            customerId = customer.getCustomerId();
            customerName = customer.getCustomerName();             
            customerEmail = customer.getCustomerEmail();
            password = customer.getPassword(); 
        }
    }
}





