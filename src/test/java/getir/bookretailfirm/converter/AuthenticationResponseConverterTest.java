package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.response.AuthenticationResponse;
import getir.bookretailfirm.service.auth.JwtTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationResponseConverterTest {

    @InjectMocks
    private AuthenticationResponseConverter authenticationResponseConverter;

    @Mock
    private JwtTokenService jwtTokenService;

    @Test
    public void it_should_convert_customer_to_authentication_response() {
        //given
        Customer customer = new Customer();
        customer.setCustomerId("1");
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");
        customer.setEmail("email");
        Optional<Customer> authUser = Optional.of(customer);

        when(jwtTokenService.generate(authUser.get())).thenReturn("token");

        //when
        AuthenticationResponse response = authenticationResponseConverter.apply(authUser.get());

        //then
        assertThat(response.getJwtToken()).isEqualTo("token");
        assertThat(response.getFirstName()).isEqualTo("FirstName");
        assertThat(response.getLastName()).isEqualTo("LastName");
        assertThat(response.getCustomerId()).isEqualTo("1");
        assertThat(response.getEmail()).isEqualTo("email");
    }
}