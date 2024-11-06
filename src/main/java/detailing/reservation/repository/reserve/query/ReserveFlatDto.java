package detailing.reservation.repository.reserve.query;

import detailing.reservation.domain.Address;
import detailing.reservation.domain.ReserveStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveFlatDto {

    private Long reserveId;
    private String name;
    private LocalDateTime localDateTime;
    private ReserveStatus reserveStatus;
    private Address address;

    private String shopName;
    private int reservePrice;
    private int reserveCount;

    public ReserveFlatDto(Long reserveId, String name, LocalDateTime localDateTime, ReserveStatus reserveStatus, Address address, String shopName, int reservePrice, int reserveCount) {
        this.reserveId = reserveId;
        this.name = name;
        this.localDateTime = localDateTime;
        this.reserveStatus = reserveStatus;
        this.address = address;
        this.shopName = shopName;
        this.reservePrice = reservePrice;
        this.reserveCount = reserveCount;
    }
}
