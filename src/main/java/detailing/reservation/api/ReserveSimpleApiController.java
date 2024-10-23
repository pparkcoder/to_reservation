package detailing.reservation.api;

import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import detailing.reservation.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne 최적화
 * Reserve
 * Reserve -> Member
 */
@RestController
@RequiredArgsConstructor
public class ReserveSimpleApiController {
    private final ReserveRepository reserveRepository;

    @GetMapping("/api/v1/simple-reserves")
    public List<Reserve> reserveV1(){
        List<Reserve> all = reserveRepository.findAllByString(new ReserveSearch());
        for (Reserve reserve : all) {
            reserve.getMember().getName(); // Lazy 강제 초기화
        }
        return all;
    }
}
