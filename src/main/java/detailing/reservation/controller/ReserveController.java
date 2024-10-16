package detailing.reservation.controller;

import detailing.reservation.domain.Member;
import detailing.reservation.domain.Reserve;
import detailing.reservation.domain.ReserveSearch;
import detailing.reservation.domain.shop.Shop;
import detailing.reservation.service.MemberService;
import detailing.reservation.service.ReserveService;
import detailing.reservation.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    private final MemberService memberService;
    private final ShopService shopService;

    @GetMapping("/reserve")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Shop> shops = shopService.findShops();

        model.addAttribute("members", members);
        model.addAttribute("shops", shops);

        return "reserve/reserveForm";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam("memberId") Long memberId,
                          @RequestParam("shopId") Long shopId,
                          @RequestParam("count") int count){
        reserveService.reserve(memberId, shopId, count);
        return "redirect:/reserves";
    }

    @GetMapping("/reserves")
    public String reserveList(@ModelAttribute("reserveSearch") ReserveSearch reserveSearch, Model model){
        List<Reserve> reserves = reserveService.findReserves(reserveSearch);
        model.addAttribute("reserves", reserves);

        return "reserve/reserveList";
    }

    @PostMapping("/reserves/{reserveId}/cancel")
    public String cancelReserve(@PathVariable("reserveId") Long reserveId){
        reserveService.cancelReserve(reserveId);
        return "redirect:/reserves";
    }
}
