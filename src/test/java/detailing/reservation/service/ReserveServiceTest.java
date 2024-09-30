package detailing.reservation.service;

import detailing.exception.NotEnoughStockException;
import detailing.reservation.domain.Address;
import detailing.reservation.domain.Member;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveStatus;
import detailing.reservation.domain.shop.OpenShop;
import detailing.reservation.repository.ReserveRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReserveServiceTest {

    @Autowired EntityManager em;
    @Autowired ReserveService reserveService;
    @Autowired ReserveRepository reserveRepository;

    @Test
    public void 업체예약() throws Exception{
        // given
        Member member = createMember("회원1");

        OpenShop op = createShop("업체1", 10000, 10);

        int reserveCount = 2;

        // when
        Long reserveId = reserveService.reserve(member.getId(), op.getId(), reserveCount);

        // then
        Reserve getReserve = reserveRepository.findOne(reserveId);
        assertEquals("예약 시 상태는 RESERVE", ReserveStatus.RESERVE, getReserve.getStatus());
        assertEquals("예약 수량이 정확해야 한다", 1, getReserve.getReserveShops().size());
        assertEquals("예약 가격은 가격 * 수량이다", 10000 * reserveCount, getReserve.getTotalPrice());
        assertEquals("예약 수량만큼 재고가 줄어야 한다", 8, op.getStockQuantity());
    }

    @Test
    public void 예약취소() throws Exception{
        // given
        Member member = createMember("회원1");
        OpenShop op = createShop("업체1", 10000, 10);

        int reserveCount = 2;
        Long reserveId = reserveService.reserve(member.getId(), op.getId(), reserveCount);

        // when
        reserveService.cancelReserve(reserveId);
        
        // then
        Reserve getReserve = reserveRepository.findOne(reserveId);
        assertEquals("예약 취소시 상태는 CANCEL", ReserveStatus.CANCEL, getReserve.getStatus());
        assertEquals("예약이 취소된 상품은 그만큼 재고 증가",10, op.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 예약수량초과() throws Exception{
        // given
        Member member = createMember("회원1");
        OpenShop op = createShop("업체1", 10000, 10);

        int reserveCount = 11;

        // when
        reserveService.reserve(member.getId(), op.getId(), reserveCount);

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울","한강대로"));
        em.persist(member);
        return member;
    }

    private OpenShop createShop(String name, int price, int stockQuantity) {
        OpenShop op = new OpenShop();
        op.setName(name);
        op.setPrice(price);
        op.setStockQuantity(stockQuantity);
        op.setAddress(new Address("서울", "한강대로"));
        em.persist(op);
        return op;
    }
}