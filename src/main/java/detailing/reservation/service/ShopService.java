package detailing.reservation.service;

import detailing.reservation.domain.shop.Shop;
import detailing.reservation.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public void saveShop(Shop shop){
        shopRepository.save(shop);
    }

    public List<Shop> findShop(){
        return shopRepository.findAll();
    }

    public Shop findOne(Long id){
        return shopRepository.findOne(id);
    }

    public List<Shop> findByName(Shop shop){
        return shopRepository.findByName(shop.getName());
    }
}
