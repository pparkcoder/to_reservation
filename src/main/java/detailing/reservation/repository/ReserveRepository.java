package detailing.reservation.repository;

import detailing.reservation.domain.Reserve;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

//    public List<Reserve> findAll
}
