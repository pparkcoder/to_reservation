package detailing.reservation.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;

    protected Address() {
    }

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }
}
