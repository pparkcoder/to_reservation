package detailing.reservation.repository.reserve.query;

import detailing.reservation.domain.Address;
import detailing.reservation.domain.ReserveStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReserveQueryDto {

    private Long reserveId;
    private String name;
    private LocalDateTime localDateTime;
    private ReserveStatus reserveStatus;
    private Address address;
    private List<ReserveShopQueryDto> reserveShops;

    public ReserveQueryDto(Long reserveId, String name, LocalDateTime localDateTime, ReserveStatus reserveStatus, Address address) {
        this.reserveId = reserveId;
        this.name = name;
        this.localDateTime = localDateTime;
        this.reserveStatus = reserveStatus;
        this.address = address;
    }
}
