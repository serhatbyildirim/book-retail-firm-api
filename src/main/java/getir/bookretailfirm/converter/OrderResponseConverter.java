package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.response.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseConverter {

    public OrderResponse apply(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setBookId(order.getBookId());
        response.setCustomerId(order.getCustomerId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        return response;
    }
}