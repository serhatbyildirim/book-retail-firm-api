package getir.bookretailfirm.controller;

import getir.bookretailfirm.model.UserAuthentication;
import getir.bookretailfirm.response.OrderResponse;
import getir.bookretailfirm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@AuthenticationPrincipal UserAuthentication userAuthentication, @RequestParam String bookId) {
        orderService.createOrder(userAuthentication.getDetails().getCustomerId(), bookId);
    }

    @GetMapping("{orderId}")
    public OrderResponse getOrderDetail(@AuthenticationPrincipal UserAuthentication userAuthentication, @PathVariable String orderId) {
        return orderService.getOrderDetail(userAuthentication.getDetails().getCustomerId(), orderId);
    }

    @GetMapping
    public List<OrderResponse> getOrderList(@AuthenticationPrincipal UserAuthentication userAuthentication) {
        return orderService.getOrderList(userAuthentication.getDetails().getCustomerId());
    }

}
