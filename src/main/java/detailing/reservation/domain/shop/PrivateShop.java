package detailing.reservation.domain.shop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("P")
public class PrivateShop extends Shop{

    private String privateTemp;
}
