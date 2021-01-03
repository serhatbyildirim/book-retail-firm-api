package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.domain.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceTest {

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @Mock
    private JwtTokenGeneratorService jwtTokenGeneratorService;

    @Captor
    private ArgumentCaptor<Map<String, Object>> tokenDataCaptor;

    @Captor
    private ArgumentCaptor<DateTime> expirationTimeCaptor;

    @Before
    public void setUp() {
        when(jwtTokenGeneratorService.generateJwtToken(any(), any())).thenReturn(RandomStringUtils.randomAlphanumeric(45));
    }

    @Test
    public void should_generate_token() {
        Customer customer = new Customer();

        String token = jwtTokenService.generate(customer);
        assertThat(token).isNotEmpty();
        verify(jwtTokenGeneratorService).generateJwtToken(expirationTimeCaptor.capture(), tokenDataCaptor.capture());

        Map<String, Object> tokenData = tokenDataCaptor.getValue();
        assertThat(tokenData).isNotNull();

        assertThat(tokenData).containsEntry("customerId", customer.getCustomerId());
        assertThat(tokenData).containsEntry("email", customer.getEmail());
        assertThat(tokenData).containsEntry("firstName", customer.getFirstName());
        assertThat(tokenData).containsEntry("lastName", customer.getLastName());

        DateTime tokenExpirationDate = expirationTimeCaptor.getValue();
        assertThat(tokenExpirationDate).isNotNull();
        assertThat(tokenExpirationDate.plusYears(-1).toDate()).isInSameDayAs(DateTime.now().toDate());
    }
}