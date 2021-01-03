package getir.bookretailfirm.repository;

import getir.bookretailfirm.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);
}
