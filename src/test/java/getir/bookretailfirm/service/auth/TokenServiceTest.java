package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.AuthenticationFailedException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtParserService jwtParserService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void it_should_throw_authenticationFailedException_when_token_not_contains_authorization_header() {
        //given

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getMessage()).isEqualTo("Authentication header not valid");
    }

    @Test
    public void it_should_throw_authenticationFailedException_when_token_not_contains_bearer() {
        //given
        given(httpServletRequest.getHeader("Authorization")).willReturn("Como");

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getMessage()).isEqualTo("Authentication header not valid");
    }

    @Test
    public void it_should_throw_authenticationFailedException_when_token_contains_invalid_header() {
        //given
        given(httpServletRequest.getHeader("Authorization")).willReturn("Bearer");

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getMessage()).isEqualTo("Authentication header not valid");
    }

    @Test
    public void it_should_throw_exception_when_token_expired() {
        //given
        Claims claims = mock(Claims.class);
        given(httpServletRequest.getHeader("Authorization")).willReturn("Bearer token");
        given(jwtParserService.decodeJWT("token")).willReturn(claims);
        given(claims.getExpiration()).willReturn(DateUtils.addDays(new Date(), -1));

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getMessage()).isEqualTo("Token expired");
    }
}