package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class SuccessPurchaseListener {

    private final Set<PostSuccessPurchaseAction> postSuccessPurchaseActions;

    SuccessPurchaseListener(Set<PostSuccessPurchaseAction> postSuccessPurchaseActions) {
        this.postSuccessPurchaseActions = postSuccessPurchaseActions;
    }

    @EventListener
    void listen(PurchaseEvent event) {
        if (event.isConfirmed()) {
            postSuccessPurchaseActions.forEach(action -> action.execute(event));
        }
    }
}
