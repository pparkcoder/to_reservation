package detailing.reservation;

import detailing.reservation.domain.Address;
import detailing.reservation.domain.Member;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveShop;
import detailing.reservation.domain.shop.OpenShop;
import detailing.reservation.domain.shop.Shop;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "서울", "1");
            em.persist(member);

            Shop op1 = createShop("업체1", 10000, 10);
            em.persist(op1);

            Shop op2 = createShop("업체2", 20000, 20);
            em.persist(op2);

            ReserveShop reserveShop1 = ReserveShop.createReserveShop(op1, 10000, 1);
            ReserveShop reserveShop2 = ReserveShop.createReserveShop(op2, 20000, 2);

            Reserve reserve = createReserve(member, reserveShop1, reserveShop2);
            em.persist(reserve);
        }

        public void dbInit2(){
            Member member = createMember("userB", "경기", "2");
            em.persist(member);

            Shop op1 = createShop("신규업체1", 15000, 15);
            em.persist(op1);

            Shop op2 = createShop("신규업체2", 25000, 25);
            em.persist(op2);

            ReserveShop reserveShop1 = ReserveShop.createReserveShop(op1, 10000, 4);
            ReserveShop reserveShop2 = ReserveShop.createReserveShop(op2, 20000, 5);

            Reserve reserve = createReserve(member, reserveShop1, reserveShop2);
            em.persist(reserve);
        }

        private Member createMember(String name, String city, String street) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street));
            return member;
        }

        private Shop createShop(String name, int price, int stockQuantity) {
            Shop op1 = new OpenShop();
            op1.setName(name);
            op1.setPrice(price);
            op1.setStockQuantity(stockQuantity);
            return op1;
        }

        private Reserve createReserve(Member member, ReserveShop reserveShop1, ReserveShop reserveShop2) {
            Reserve reserve = Reserve.createReserve(member, reserveShop1, reserveShop2);
            return reserve;
        }
    }
}

