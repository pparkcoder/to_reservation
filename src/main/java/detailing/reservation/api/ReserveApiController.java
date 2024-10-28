package detailing.reservation.api;

import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import detailing.reservation.domain.ReserveShop;
import detailing.reservation.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReserveApiController {

    private final ReserveRepository reserveRepository;

    @GetMapping("/api/v1/reserves")
    public List<Reserve> reserveV1(){
        List<Reserve> all = reserveRepository.findAllByString(new ReserveSearch());
        for (Reserve reserve : all) {
            reserve.getMember().getName();
            List<ReserveShop> reserveShops = reserve.getReserveShops();
            reserveShops.stream().forEach(r -> r.getShop().getName());
        }
        return all;
    }
}
