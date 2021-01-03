package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.enums.OrderStatus;
import getir.bookretailfirm.response.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderResponseConverterTest {

    @InjectMocks
    private OrderResponseConverter orderResponseConverter;

    @Test
    public void it_should_convert() {
        // given
        Order order = new Order();
        order.setOrderId("orderId");
        order.setBookId("bookId");
        order.setCustomerId("customerId");
        order.setStatus(OrderStatus.NEW_ORDER);

        // when
        OrderResponse orderResponse = orderResponseConverter.apply(order);

        // then
        assertThat(orderResponse.getCustomerId()).isEqualTo("customerId");
        assertThat(orderResponse.getBookId()).isEqualTo("bookId");
        assertThat(orderResponse.getOrderId()).isEqualTo("orderId");
        assertThat(orderResponse.getStatus()).isEqualTo(OrderStatus.NEW_ORDER);
    }
}