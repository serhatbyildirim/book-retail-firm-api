package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private static final int EXPIRE_YEAR = 1;
    private static final String JWT_EMAIL = "email";
    private static final String JWT_CUSTOMER_ID = "customerId";
    private static final String JWT_FIRST_NAME = "firstName";
    private static final String JWT_LAST_NAME = "lastName";

    private final JwtTokenGeneratorService jwtTokenGeneratorService;

    public String generate(Customer customer) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put(JWT_CUSTOMER_ID, customer.getCustomerId());
        tokenData.put(JWT_FIRST_NAME, customer.getFirstName());
        tokenData.put(JWT_LAST_NAME, customer.getLastName());
        tokenData.put(JWT_EMAIL, customer.getEmail());
        DateTime expirationTime = DateTime.now().plusYears(EXPIRE_YEAR);
        return jwtTokenGeneratorService.generateJwtToken(expirationTime, tokenData);
    }
}
