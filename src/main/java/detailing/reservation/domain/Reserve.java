package detailing.reservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reserve {

    @Id
    @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // FK 설정, 연관관계 주인
    private Member member;

    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL)
    private List<ReserveShop> reserveShops = new ArrayList<>();

    private LocalDateTime reserveDate; // 예약 시간

    @Enumerated(EnumType.STRING) // Default : ORDINAL(숫자로 들어감) -> 중간에 상태가 추가되면 기존에 숫자가 유지되므로 상태가 꼬임
    private ReserveStatus status; // 예약 상태 : RESERVE, CANCEL

    // 연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getReserves().add(this);
    }

    public void addReserveShop(ReserveShop reserveShop){
        reserveShops.add(reserveShop);
        reserveShop.setReserve(this);
    }

    // 생성 메서드
    public static Reserve createReserve(Member member, ReserveShop... reserveShops){
        Reserve reserve = new Reserve();
        reserve.setMember(member);
        for (ReserveShop reserveShop : reserveShops) {
            reserve.addReserveShop(reserveShop);
        }
        reserve.setReserveDate(LocalDateTime.now());
        reserve.setStatus(ReserveStatus.RESERVE);
        return reserve;
    }

    /**
     * 예약 취소
     */
    public void cancel(){
        this.setStatus(ReserveStatus.CANCEL);
        for (ReserveShop reserveShop : reserveShops){
            reserveShop.cancel();
        }
    }

    /**
     * 결제 예정 금액 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (ReserveShop reserveShop : reserveShops){
            totalPrice += reserveShop.getTotalPrice();
        }
        return totalPrice;
    }
}
