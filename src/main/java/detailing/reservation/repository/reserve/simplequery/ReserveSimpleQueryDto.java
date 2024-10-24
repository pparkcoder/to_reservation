package detailing.reservation.repository.reserve.simplequery;

import detailing.reservation.domain.Address;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveSimpleQueryDto {

    private Long reserveId;
    private String name;
    private LocalDateTime reserveData;
    private ReserveStatus reserveStatus;
    private Address address;

    public ReserveSimpleQueryDto(Long reserveId, String name, LocalDateTime reserveData, ReserveStatus reserveStatus, Address address) {
        this.reserveId = reserveId;
        this.name = name;
        this.reserveData = reserveData;
        this.reserveStatus = reserveStatus;
        this.address = address;
    }
}
