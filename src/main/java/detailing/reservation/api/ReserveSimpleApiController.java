package detailing.reservation.api;

import detailing.reservation.domain.Address;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import detailing.reservation.domain.ReserveStatus;
import detailing.reservation.repository.ReserveRepository;
import detailing.reservation.repository.reserve.simplequery.ReserveSimpleQueryDto;
import detailing.reservation.repository.reserve.simplequery.ReserveSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne 최적화
 * Reserve
 * Reserve -> Member
 */
@RestController
@RequiredArgsConstructor
public class ReserveSimpleApiController {
    private final ReserveRepository reserveRepository;
    private final ReserveSimpleQueryRepository reserveSimpleQueryRepository;

    @GetMapping("/api/v1/simple-reserves")
    public List<Reserve> reserve1(){
        List<Reserve> all = reserveRepository.findAllByString(new ReserveSearch());
        for (Reserve reserve : all) {
            reserve.getMember().getName(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-reserves")
    public List<SimpleReserveDto> reserveV2(){
        List<Reserve> reserves = reserveRepository.findAllByString(new ReserveSearch());
        List<SimpleReserveDto> result = reserves.stream()
                .map(r -> new SimpleReserveDto(r))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-reserves")
    public List<SimpleReserveDto> reserveV3(){
        List<Reserve> reserves = reserveRepository.findAllWithMember();
        List<SimpleReserveDto> result = reserves.stream()
                .map(r -> new SimpleReserveDto(r))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-reserves")
    public List<ReserveSimpleQueryDto> reserveV4(){
        return reserveSimpleQueryRepository.findReserveDtos();
    }

    @Data
    static class SimpleReserveDto{
        private Long reserveId;
        private String name;
        private LocalDateTime reserveData;
        private ReserveStatus reserveStatus;
        private Address address;

        public SimpleReserveDto(Reserve reserve) {
            reserveId = reserve.getId();
            name = reserve.getMember().getName();
            reserveData = reserve.getReserveDate();
            reserveStatus = reserve.getStatus();
            address = reserve.getMember().getAddress();
        }
    }
}
