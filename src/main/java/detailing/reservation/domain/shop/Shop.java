package detailing.reservation.domain.shop;

import detailing.exception.NotEnoughStockException;
import detailing.reservation.domain.Address;
import detailing.reservation.domain.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글 테이블 전략
@DiscriminatorColumn(name = "dtype")
public abstract class Shop {

    @Id
    @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    private String name;
    private String phone;
    private Address address;
    private int price;
    private int stockQuantity;

    // 연관관계 메서드
    public void setSales(Sales sales){
        this.sales = sales;
        sales.setShop(this);
    }

    // 비즈니스 로직
    /**
     * stock 증가
     */
    public void addStock(int quantity)
    {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int resStock = this.stockQuantity - quantity;
        if(resStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resStock;
    }
}
