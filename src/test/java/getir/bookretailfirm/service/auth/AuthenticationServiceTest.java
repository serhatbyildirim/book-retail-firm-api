package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.converter.IdentityUserConverter;
import getir.bookretailfirm.model.IdentityUser;
import getir.bookretailfirm.model.UserAuthentication;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private IdentityUserConverter identityUserConverter;

    @Test
    public void it_should_get_authentication() {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        IdentityUser identityUser = new IdentityUser();
        given(tokenService.getTokenClaims(httpServletRequest)).willReturn(claims);
        given(identityUserConverter.apply(claims)).willReturn(identityUser);

        //when
        UserAuthentication userAuthentication = (UserAuthentication) authenticationService.getUserAuthentication(httpServletRequest);

        //then
        assertThat(userAuthentication.getDetails()).isEqualTo(identityUser);
    }

}