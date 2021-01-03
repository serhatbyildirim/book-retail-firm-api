package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.OrderConverter;
import getir.bookretailfirm.converter.OrderResponseConverter;
import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.domain.Order;
import getir.bookretailfirm.exception.NotFoundException;
import getir.bookretailfirm.repository.BookRepository;
import getir.bookretailfirm.repository.OrderRepository;
import getir.bookretailfirm.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderResponseConverter orderResponseConverter;

    public void createOrder(String customerId, String bookId) {
        Book book = bookRepository.findById(bookId);

        if (book.getStock() <= 0) {
            throw new NotFoundException("Not enough stock found");
        }

        Order order = orderConverter.apply(customerId, bookId);
        orderRepository.save(order);

        bookRepository.update(book);

        log.info("New order created. Book id : #{} and Customer id placing the order : #{}", bookId, customerId);
    }

    public OrderResponse getOrderDetail(String customerId, String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new NotFoundException("Order not found by id : " + orderId);
        }

        if (!optionalOrder.get().getCustomerId().equals(customerId)) {
            throw new AccessDeniedException("You have not permission to view this order");
        }
        return orderResponseConverter.apply(optionalOrder.get());
    }

    public List<OrderResponse> getOrderList(String customerId) {
        List<Order> orderList = orderRepository.findByCustomerId(customerId);
        return orderList.stream().map(orderResponseConverter::apply).collect(Collectors.toList());
    }
}
