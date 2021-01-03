package getir.bookretailfirm.repository;

import getir.bookretailfirm.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    public Optional<Customer> findByEmailAndPassword(String email, String password);

}
