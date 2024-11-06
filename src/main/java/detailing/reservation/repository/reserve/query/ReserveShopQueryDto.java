package detailing.reservation.repository.reserve.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ReserveShopQueryDto {

    @JsonIgnore
    private Long reserveId;
    private String shopName;
    private int reservePrice;
    private int reserveCount;

    public ReserveShopQueryDto(Long reserveId, String shopName, int reservePrice, int reserveCount) {
        this.reserveId = reserveId;
        this.shopName = shopName;
        this.reservePrice = reservePrice;
        this.reserveCount = reserveCount;
    }
}


