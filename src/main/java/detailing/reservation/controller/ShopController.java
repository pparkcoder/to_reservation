package detailing.reservation.controller;

import detailing.reservation.domain.shop.OpenShop;
import detailing.reservation.domain.shop.Shop;
import detailing.reservation.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/shops/new")
    public String createForm(Model model){
        model.addAttribute("form", new ShopForm());
        return "shops/createShopForm";
    }

    @PostMapping("/shops/new")
    public String create(ShopForm form){
        OpenShop shop = new OpenShop();
        shop.setName(form.getName());
        shop.setPrice(form.getPrice());
        shop.setStockQuantity(form.getStockQuantity());

        shopService.saveShop(shop);
        return "redirect:/";
    }

    @GetMapping("/shops")
    public String list(Model model){
        List<Shop> shops = shopService.findShops();
        model.addAttribute("shops", shops);
        return "shops/shopList";
    }
}
