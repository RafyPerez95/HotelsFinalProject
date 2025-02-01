package hotels.finals.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import hotels.finals.controller.model.HotelData;
import hotels.finals.controller.model.HotelData.CustomerData;
import hotels.finals.controller.model.HotelData.RoomData;
import hotels.finals.service.HotelsFinalService;

@RestController
@RequestMapping("/hotels")
public class HotelsFinalController {

  @Autowired
  private HotelsFinalService hotelNameService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public HotelData createHotel(@RequestBody HotelData hotelName) {
    return hotelNameService.saveHotel(hotelName);
  }

  @PutMapping("/{hotelId}")
  public HotelData updateHotel(@PathVariable Long hotelId, @RequestBody HotelData hotelName) {
    hotelName.setHotelId(hotelId);
    return hotelNameService.saveHotel(hotelName);
  }

  @GetMapping
  public List<HotelData> getAllHotels() {
    return hotelNameService.retrieveAllHotels();
  }

  @GetMapping("/{hotelId}")
  public HotelData getHotelById(@PathVariable Long hotelId) {
    return hotelNameService.retrieveHotelById(hotelId);
  }

  @DeleteMapping("/{hotelId}")
  public Map<String, String> deleteHotel(@PathVariable Long hotelId) {
    hotelNameService.deleteHotelById(hotelId);
    return Map.of("Succes", "Hotel with ID " + hotelId + "deleted.");
  }

  @PostMapping("/{hotelId}/room")
  @ResponseStatus(code = HttpStatus.CREATED)
  public RoomData createRoom(@RequestBody RoomData roomData, @PathVariable Long hotelId) {

    return hotelNameService.saveRoom(hotelId, roomData);

}
  
  @PostMapping("/{hotelId}/customer")
  @ResponseStatus(code = HttpStatus.CREATED)
  public CustomerData createCustomer(@RequestBody CustomerData customerData, @PathVariable Long hotelId) {

    return hotelNameService.saveCustomer(hotelId, customerData);



  } 

}

