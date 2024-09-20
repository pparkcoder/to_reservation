package detailing.reservation.domain;

import detailing.reservation.domain.shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReserveShop {

    @Id
    @GeneratedValue
    @Column(name = "reserve_shop_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserve_id") // FK 설정, 연관관계 주인
    private Reserve reserve;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private int reservePrice; // 예약 가격
    private int reserveCount; // 예약 차량
}
