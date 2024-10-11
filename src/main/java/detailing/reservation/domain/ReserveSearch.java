package detailing.reservation.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveSearch {

    private String memberName; // 회원 이름
    private ReserveStatus reserveStatus; // 예약 상태 [RESERVE, CANCEL]
}
