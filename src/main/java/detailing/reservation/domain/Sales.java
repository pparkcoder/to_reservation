package detailing.reservation.domain;

import detailing.reservation.domain.shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sales {

    @Id
    @GeneratedValue
    @Column(name = "sales_id")
    private Long id;

    @OneToOne(mappedBy = "sales")
    private Shop shop;

    private LocalDateTime reserveDate; // 매출발생 일자
    private int reservePrice; // 매출 가격
    private int reserveCount; // 예약 수량
}
