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

    // 변경 감지 적용
    @Transactional
    public Shop updateShop(Long shopId, String name, int price, int stockQuantity){
        Shop findShop = shopRepository.findOne(shopId);
        findShop.setName(name);
        findShop.setPrice(price);
        findShop.setStockQuantity(stockQuantity);
        return findShop;
    }
    public List<Shop> findShops(){
        return shopRepository.findAll();
    }

    public Shop findOne(Long id){
        return shopRepository.findOne(id);
    }

    public List<Shop> findByName(Shop shop){
        return shopRepository.findByName(shop.getName());
    }
}
