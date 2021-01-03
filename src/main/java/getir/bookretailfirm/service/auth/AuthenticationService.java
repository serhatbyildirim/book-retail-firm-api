package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.converter.AuthenticationResponseConverter;
import getir.bookretailfirm.converter.IdentityUserConverter;
import getir.bookretailfirm.domain.Customer;
import getir.bookretailfirm.exception.AuthenticationFailedException;
import getir.bookretailfirm.model.UserAuthentication;
import getir.bookretailfirm.repository.CustomerRepository;
import getir.bookretailfirm.request.AuthenticationRequest;
import getir.bookretailfirm.response.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final IdentityUserConverter identityUserConverter;
    private final EncryptionService encryptionService;
    private final CustomerRepository customerRepository;
    private final AuthenticationResponseConverter authenticationResponseConverter;

    public Authentication getUserAuthentication(HttpServletRequest httpServletRequest) {
        Claims claims = tokenService.getTokenClaims(httpServletRequest);
        return new UserAuthentication(identityUserConverter.apply(claims));
    }

    public AuthenticationResponse getToken(AuthenticationRequest authenticationRequest) {
        String encryptedPassword = encryptionService.encrypt(authenticationRequest.getPassword());
        Optional<Customer> authCustomer = customerRepository.findByEmailAndPassword(authenticationRequest.getEmail(), encryptedPassword);

        if (authCustomer.isEmpty()) {
            throw new AuthenticationFailedException(authenticationRequest.getEmail());
        }

        return authenticationResponseConverter.apply(authCustomer.get());
    }
}
