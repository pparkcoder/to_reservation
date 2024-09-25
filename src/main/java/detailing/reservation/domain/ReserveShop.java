package detailing.reservation.domain;

import detailing.reservation.domain.shop.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReserveShop {

    @Id
    @GeneratedValue
    @Column(name = "reserve_shop_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id") // FK 설정, 연관관계 주인
    private Reserve reserve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private int reservePrice; // 예약 가격
    private int reserveCount; // 예약 차량

    // 생성 메서드
    public static ReserveShop createReserveShop(Shop shop, int reservePrice, int reserveCount){
        ReserveShop reserveShop = new ReserveShop();
        reserveShop.setShop(shop);
        reserveShop.setReservePrice(reservePrice);
        reserveShop.setReserveCount(reserveCount);

        shop.removeStock(reserveCount);
        return reserveShop;
    }
    // 비즈니스 로직

    /**
     * 예약 취소 시 예약 가능 횟수 원복
     */
    public void cancel() {
        getShop().addStock(reserveCount);
    }

    /**
     * 결제 예정 금액 조회
     */
    public int getTotalPrice() {
        return getReservePrice() * getReserveCount();
    }
}
