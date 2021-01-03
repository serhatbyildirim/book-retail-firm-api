package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.response.AuthenticationResponse;
import getir.bookretailfirm.service.auth.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationResponseConverter {

    private final JwtTokenService jwtTokenService;

    public AuthenticationResponse apply(Customer customer) {
        String jwtToken = jwtTokenService.generate(customer);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwtToken(jwtToken);
        authenticationResponse.setFirstName(customer.getFirstName());
        authenticationResponse.setLastName(customer.getLastName());
        authenticationResponse.setCustomerId(customer.getCustomerId());
        authenticationResponse.setEmail(customer.getEmail());
        return authenticationResponse;
    }
}
