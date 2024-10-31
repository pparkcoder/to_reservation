package detailing.reservation.repository;

import detailing.reservation.domain.Member;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {

    private final EntityManager em;

    public void save(Reserve reserve){
        em.persist(reserve);
    }

    public Reserve findOne(Long id){
        return em.find(Reserve.class, id);
    }

    public List<Reserve> findAll(ReserveSearch reserveSearch){
        return em.createQuery("select r from Reserve r join r.member m" +
                " where r.status = :status " +
                " and m.name like :name", Reserve.class)
                .setParameter("status",reserveSearch.getReserveStatus())
                .setParameter("name",reserveSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Reserve> findAllByString(ReserveSearch reserveSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reserve> cq = cb.createQuery(Reserve.class);
        Root<Reserve> o = cq.from(Reserve.class);
        Join<Reserve, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (reserveSearch.getReserveStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    reserveSearch.getReserveStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(reserveSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + reserveSearch.getMemberName()
                            + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Reserve> query = em.createQuery(cq).setMaxResults(1000); //최대 1000 건
        return query.getResultList();
    }

    public List<Reserve> findAllWithMember() {
        return em.createQuery(
                "select r from Reserve r" +
                        " join fetch r.member m", Reserve.class
        ).getResultList();
    }

    public List<Reserve> findAllWithShop() {
        return em.createQuery(
                "select distinct r from Reserve r" +
                        " join fetch r.member" +
                        " join fetch r.reserveShops rs" +
                        " join fetch rs.shop s", Reserve.class)
                .getResultList();
    }
}
