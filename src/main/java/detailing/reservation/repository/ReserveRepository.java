package detailing.reservation.repository;

import detailing.reservation.domain.Reserve;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
