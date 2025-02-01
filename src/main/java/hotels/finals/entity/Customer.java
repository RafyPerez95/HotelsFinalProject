package hotels.finals.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long CustomerId;

private String customerName;
private String customerEmail;
private String password;
@EqualsAndHashCode.Exclude
@ToString.Exclude
@ManyToMany(mappedBy = "customers", cascade = CascadeType.ALL)


private Set<Hotel> hotelNames = new HashSet<>();

}
