package detailing.reservation.repository;

import detailing.reservation.domain.Member;
import detailing.reservation.domain.shop.Shop;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopRepository {

    private final EntityManager em;

    public void save(Shop shop){
        if(shop.getId() == null) {
            em.persist(shop);
        }
        else{
            em.merge(shop);
        }
    }

    public Shop findOne(Long id){
        return em.find(Shop.class, id);
    }

    public List<Shop> findAll(){
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    public List<Shop> findByName(String name){
        return em.createQuery("select s from Shop s where s.name = :name", Shop.class)
                .setParameter("name", name)
                .getResultList();
    }
}
