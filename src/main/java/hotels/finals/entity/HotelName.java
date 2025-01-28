package hotels.finals.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@Data

@Entity
public class HotelName {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long hotelId;
  
  private String name;
  private String address;
  private String state;
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
  private Set<Room> rooms = new HashSet<>();
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "hotel_name_customer",
  joinColumns = @JoinColumn(name = "hotel_id"),
  inverseJoinColumns = @JoinColumn(name = "customer_id"))
  
 private Set<Customer> customers = new HashSet<>();
  


}