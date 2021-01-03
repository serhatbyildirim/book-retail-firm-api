package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.enums.OrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {

    @InjectMocks
    private OrderConverter orderConverter;

    @Test
    public void it_should_convert() {
        // given
        // when
        Order order = orderConverter.apply("customerId", "bookId");

        // then
        assertThat(order.getCustomerId()).isEqualTo("customerId");
        assertThat(order.getBookId()).isEqualTo("bookId");
        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW_ORDER);
    }

}