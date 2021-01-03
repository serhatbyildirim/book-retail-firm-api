package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.request.RegisterRequest;
import getir.bookretailfirm.service.auth.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerConverter {

    private final EncryptionService encryptionService;

    public Customer apply(RegisterRequest registerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(registerRequest.getFirstName());
        customer.setLastName(registerRequest.getLastName());
        customer.setEmail(registerRequest.getEmail());
        customer.setPassword(encryptionService.encrypt(registerRequest.getPassword()));
        return customer;
    }
}