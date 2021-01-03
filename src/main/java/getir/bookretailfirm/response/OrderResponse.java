package getir.bookretailfirm.response;

import getir.bookretailfirm.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {

    private String orderId;
    private String customerId;
    private String bookId;
    private OrderStatus status;
    private Date orderDate;
}
