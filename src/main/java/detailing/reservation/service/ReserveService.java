package detailing.reservation.service;

import detailing.reservation.domain.Member;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import detailing.reservation.domain.ReserveShop;
import detailing.reservation.domain.shop.Shop;
import detailing.reservation.repository.MemberRepository;
import detailing.reservation.repository.ReserveRepository;
import detailing.reservation.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    /**
     * 예약 등록
     */
    @Transactional
    public Long reserve(Long memberId, Long shopId, int count){
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Shop shop = shopRepository.findOne(shopId);

        // 예약업체 생성
        ReserveShop reserveShop = ReserveShop.createReserveShop(shop, shop.getPrice(), count);

        // 예약 생성
        Reserve reserve = Reserve.createReserve(member, reserveShop);

        // 예약 저장
        reserveRepository.save(reserve);

        return reserve.getId();
    }
    /**
     * 예약 취소
     */
    @Transactional
    public void cancelReserve(Long reserveId){
        // 예약 엔티티 조회
        Reserve reserve = reserveRepository.findOne(reserveId);
        // 예약 취소
        reserve.cancel();
    }

    /**
     * 예약 조회
     */
    public List<Reserve> findReserves(ReserveSearch reserveSearch){
        return reserveRepository.findAllByString(reserveSearch);
    }
}
