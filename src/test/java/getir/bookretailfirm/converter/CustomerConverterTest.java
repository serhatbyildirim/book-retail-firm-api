package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.request.RegisterRequest;
import getir.bookretailfirm.service.auth.EncryptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CustomerConverterTest {

    @InjectMocks
    private CustomerConverter customerConverter;

    @Mock
    private EncryptionService encryptionService;

    @Test
    public void it_should_convert() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        registerRequest.setPassword("password");

        given(encryptionService.encrypt("password")).willReturn("encryptedPassword");

        // when
        Customer customer = customerConverter.apply(registerRequest);

        // then
        assertThat(customer.getFirstName()).isEqualTo("firstName");
        assertThat(customer.getLastName()).isEqualTo("lastName");
        assertThat(customer.getEmail()).isEqualTo("email");
        assertThat(customer.getPassword()).isEqualTo("encryptedPassword");
    }

}