package detailing.reservation.repository.reserve.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveSimpleQueryRepository {

    private final EntityManager em;

    public List<ReserveSimpleQueryDto> findReserveDtos(){
        return em.createQuery(
                "select new detailing.reservation.repository.reserve.simplequery.ReserveSimpleQueryDto(r.id,r.member.name,r.reserveDate,r.status,r.member.address)" +
                        " from Reserve r" +
                        " join r.member m", ReserveSimpleQueryDto.class
        ).getResultList();
    }

}
