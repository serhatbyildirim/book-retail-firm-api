package getir.bookretailfirm.converter;

import getir.bookretailfirm.model.IdentityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IdentityUserConverterTest {

    @InjectMocks
    private IdentityUserConverter identityUserConverter;

    @Test
    public void it_should_convert() {
        //given
        Map<String, Object> claimsMap = new HashMap<>();
        Claims claims = new DefaultClaims(claimsMap);

        claimsMap.put("customerId", "1");
        claimsMap.put("email", "email");
        claimsMap.put("firstName", "firstname");
        claimsMap.put("lastName", "lastname");

        //when
        IdentityUser identityUser = identityUserConverter.apply(claims);

        //then
        assertThat(identityUser.getCustomerId()).isEqualTo("1");
        assertThat(identityUser.getEmail()).isEqualTo("email");
        assertThat(identityUser.getFirstName()).isEqualTo("firstname");
        assertThat(identityUser.getLastName()).isEqualTo("lastname");
    }
}