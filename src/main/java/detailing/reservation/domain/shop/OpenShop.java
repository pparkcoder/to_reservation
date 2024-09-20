package detailing.reservation.domain.shop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("O")
public class OpenShop extends Shop{

    private String openTemp;
}
