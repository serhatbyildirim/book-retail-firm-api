package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.CustomerConverter;
import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.repository.CustomerRepository;
import getir.bookretailfirm.request.RegisterRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerConverter customerConverter;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void it_should_save_customer() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();

        Customer customer = new Customer();
        given(customerConverter.apply(registerRequest)).willReturn(customer);

        // when
        customerService.saveCustomer(registerRequest);
        // then

        verify(customerRepository).save(customer);
    }
}