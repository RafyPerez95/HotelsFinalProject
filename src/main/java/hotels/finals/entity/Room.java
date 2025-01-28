package hotels.finals.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data

@Entity
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;
  
  private String roomType;
  private int bedrooms;
  private String view;
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hotel_id", nullable = false)
  private HotelName hotel;
  

}
