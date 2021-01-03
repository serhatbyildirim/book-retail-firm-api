package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.OrderConverter;
import getir.bookretailfirm.converter.OrderResponseConverter;
import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.enums.OrderStatus;
import getir.bookretailfirm.exception.NotFoundException;
import getir.bookretailfirm.repository.BookRepository;
import getir.bookretailfirm.repository.OrderRepository;
import getir.bookretailfirm.response.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private OrderResponseConverter orderResponseConverter;

    @Test
    public void it_should_create_order() {
        // given
        Book book = new Book("id", "name", 10);
        given(bookRepository.findById("bookId")).willReturn(book);

        Order order = new Order();
        given(orderConverter.apply("customerId", "bookId")).willReturn(order);

        // when
        orderService.createOrder("customerId", "bookId");

        // then
        verify(orderRepository).save(order);
        verify(bookRepository).update(book);
    }

    @Test
    public void it_should_throw_exception_when_book_has_not_enough_stock() {
        // given
        Book book = new Book("id", "name", 0);
        given(bookRepository.findById("bookId")).willReturn(book);

        // when
        Throwable throwable = catchThrowable(() -> orderService.createOrder("customerId", "bookId"));

        // then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable).hasMessage("Not enough stock found");
    }

    @Test
    public void it_should_get_order_detail() {
        // given
        Order order = new Order("orderId", "customerId", "bId", new Date(), OrderStatus.NEW_ORDER);
        given(orderRepository.findById("orderId")).willReturn(Optional.of(order));

        OrderResponse orderResponse = new OrderResponse();
        given(orderResponseConverter.apply(order)).willReturn(orderResponse);

        // when
        OrderResponse result = orderService.getOrderDetail("customerId", "orderId");

        // then
        assertThat(result).isEqualTo(orderResponse);
    }

    @Test
    public void it_should_throw_exception_when_order_is_not_found() {
        // given
        given(orderRepository.findById("orderId")).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> orderService.getOrderDetail("customerId", "orderId"));

        // then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable).hasMessage("Order not found by id : orderId");
    }

    @Test
    public void it_should_throw_exception_when_customer_has_not_permission_to_view_order() {
        // given
        Order order = new Order("orderId", "differentId", "bId", new Date(), OrderStatus.NEW_ORDER);
        given(orderRepository.findById("orderId")).willReturn(Optional.of(order));

        // when
        Throwable throwable = catchThrowable(() -> orderService.getOrderDetail("customerId", "orderId"));

        // then
        assertThat(throwable).isInstanceOf(AccessDeniedException.class);
        assertThat(throwable).hasMessage("You have not permission to view this order");
    }

    @Test
    public void it_should_get_order_list() {
        // given
        Order order1 = new Order();
        Order order2 = new Order();
        given(orderRepository.findByCustomerId("customerId")).willReturn(Arrays.asList(order1, order2));

        OrderResponse orderResponse1 = new OrderResponse();
        given(orderResponseConverter.apply(order1)).willReturn(orderResponse1);
        OrderResponse orderResponse2 = new OrderResponse();
        given(orderResponseConverter.apply(order2)).willReturn(orderResponse2);

        // when
        List<OrderResponse> resultList = orderService.getOrderList("customerId");

        // then
        assertThat(resultList).containsExactly(orderResponse1, orderResponse2);
    }
}