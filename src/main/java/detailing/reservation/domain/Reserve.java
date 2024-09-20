package detailing.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Reserve {

    @Id
    @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // FK 설정, 연관관계 주인
    private Member member;

    @OneToMany(mappedBy = "reserve")
    private List<ReserveShop> reserveShops = new ArrayList<>();

    private LocalDateTime reserveDate; // 예약 시간

    @Enumerated(EnumType.STRING) // Default : ORDINAL(숫자로 들어감) -> 중간에 상태가 추가되면 기존에 숫자가 유지되므로 상태가 꼬임
    private ReserveStatus status; // 예약 상태 : RESERVE, CANCEL
}
