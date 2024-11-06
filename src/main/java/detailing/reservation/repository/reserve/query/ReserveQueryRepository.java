package detailing.reservation.repository.reserve.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveQueryRepository {

    private final EntityManager em;
    
    public List<ReserveQueryDto> findReserveQueryDtos(){
        List<ReserveQueryDto> result = findReserves();
        result.forEach(r -> {
            List<ReserveShopQueryDto> reserveShops = findReserveShops(r.getReserveId());
            r.setReserveShops(reserveShops);
        });
        return result;
    }

    private List<ReserveShopQueryDto> findReserveShops(Long reserveId) {
        return em.createQuery(
                "select new detailing.reservation.repository.reserve.query.ReserveShopQueryDto(rs.id, s.name, rs.reservePrice, rs.reserveCount)" +
                        " from ReserveShop rs" +
                        " join rs.shop s" +
                        " where rs.reserve.id = :reserveId", ReserveShopQueryDto.class)
                .setParameter("reserveId", reserveId)
                .getResultList();
    }

    public List<ReserveQueryDto> findReserves(){
        return em.createQuery(
                "select new detailing.reservation.repository.reserve.query.ReserveQueryDto(r.id, m.name, r.reserveDate, r.status, m.address)" +
                        " from Reserve r" +
                        " join r.member m", ReserveQueryDto.class)
                .getResultList();
    }
}

