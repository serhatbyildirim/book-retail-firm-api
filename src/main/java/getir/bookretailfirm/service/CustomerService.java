package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.CustomerConverter;
import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.repository.CustomerRepository;
import getir.bookretailfirm.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;

    public void saveCustomer(RegisterRequest registerRequest) {
        Customer customer = customerConverter.apply(registerRequest);
        customerRepository.save(customer);
        log.info("New Customer Successfully Saved. Customer Id : " + customer.toString());
    }
}
