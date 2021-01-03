package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderConverter {

    public Order apply(String customerId, String bookId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());
        order.setBookId(bookId);
        order.setStatus(OrderStatus.NEW_ORDER);
        return order;
    }
}