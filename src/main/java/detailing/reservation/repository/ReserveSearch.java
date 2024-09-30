package detailing.reservation.repository;

import detailing.reservation.domain.ReserveStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveSearch {

    private String memberName; // 회원 이름
    private ReserveStatus reserveStatus; // RESERVE, CANCEL

}
