package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class OuterSystemsMockController {

    @PostMapping("/invoice/register")
    ResponseEntity<?> invoice(@RequestBody Map<String, Object> request) {
        System.out.println(request);

        return ok().build();
    }

    @PostMapping("/sellerRanking/newPurchase")
    ResponseEntity<?> newPurchase(@RequestBody Map<String, Object> request) {
        System.out.println(request);

        return ok().build();
    }
}
