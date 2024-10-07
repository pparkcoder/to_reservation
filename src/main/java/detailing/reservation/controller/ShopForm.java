package detailing.reservation.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
