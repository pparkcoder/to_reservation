package detailing.reservation.repository.reserve.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReserveQueryRepository {

    private final EntityManager em;

    // v4에서 사용
    public List<ReserveQueryDto> findReserveQueryDtos(){
        List<ReserveQueryDto> result = findReserves();
        result.forEach(r -> {
            List<ReserveShopQueryDto> reserveShops = findReserveShops(r.getReserveId());
            r.setReserveShops(reserveShops);
        });
        return result;
    }

    // v5에서 사용
    public List<ReserveQueryDto> findAllByDto_optimization() {
        List<ReserveQueryDto> result = findReserves();

        Map<Long, List<ReserveShopQueryDto>> reserveShopMap = findReservShopMap(toReserveIds(result));

        result.forEach(r -> r.setReserveShops(reserveShopMap.get(r.getReserveId())));

        return result;
    }

    private List<Long> toReserveIds(List<ReserveQueryDto> result) {
        List<Long> reserveIds = result.stream()
                .map(r -> r.getReserveId())
                .collect(Collectors.toList());
        return reserveIds;
    }

    private Map<Long, List<ReserveShopQueryDto>> findReservShopMap(List<Long> reserveIds) {
        List<ReserveShopQueryDto> reserveShops = em.createQuery(
                        "select new detailing.reservation.repository.reserve.query.ReserveShopQueryDto(rs.reserve.id, s.name, rs.reservePrice, rs.reserveCount)" +
                                " from ReserveShop rs" +
                                " join rs.shop s" +
                                " where rs.reserve.id in :reserveIds", ReserveShopQueryDto.class)
                .setParameter("reserveIds", reserveIds)
                .getResultList();

        Map<Long, List<ReserveShopQueryDto>> reserveShopMap = reserveShops.stream()
                .collect(Collectors.groupingBy(reserveShopQueryDto -> reserveShopQueryDto.getReserveId()));
        return reserveShopMap;
    }

    private List<ReserveShopQueryDto> findReserveShops(Long reserveId) {
        return em.createQuery(
                "select new detailing.reservation.repository.reserve.query.ReserveShopQueryDto(rs.reserve.id, s.name, rs.reservePrice, rs.reserveCount)" +
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

    // v6에서 사용
    public List<ReserveFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new "+
                        "detailing.reservation.repository.reserve.query.ReserveFlatDto(r.id, m.name, r.reserveDate, r.status, m.address, s.name, rs.reservePrice, rs.reserveCount)" +
                        " from Reserve r" +
                        " join r.member m" +
                        " join r.reserveShops rs" +
                        " join rs.shop s", ReserveFlatDto.class)
                .getResultList();
    }
}

