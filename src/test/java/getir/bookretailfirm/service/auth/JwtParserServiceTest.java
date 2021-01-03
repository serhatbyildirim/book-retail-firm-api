package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.TokenParserException;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JwtParserService.class)
public class JwtParserServiceTest {

    @Autowired
    private JwtParserService jwtParserService;

    @Value("${security.token.secret.key}")
    private String secretKey;

    @Test
    public void it_should_decode_jwt_token() {
        // given
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJmaXJzdE5hbWUiOiJ0ZXN0IiwibGFzdE5hbWUiOiJ0ZXN0IiwiY3VzdG9tZXJJZCI6IjgwMTc2ZmQzLWMwZjUtNDA3OS1hZWRkLTk2M2RkYWQwOGQwMCIsImVtYWlsIjoidGVzdEBnbWFpbC5jb20iLCJleHAiOjE2NDEyMjM1NjgsImlhdCI6MTYwOTYyMTIwMH0.4FezXfkcTFbYnxGmHAfxEl9MUZj6vSwRMdMdd-x59jDumG967WfJ1MgtexEl5gg-u_-G8qgS91GT80F1xPNJIA";

        // when
        Claims claims = jwtParserService.decodeJWT(jwt);

        // then
        assertThat(claims.get("email")).isEqualTo("test@gmail.com");
        assertThat(claims.get("firstName")).isEqualTo("test");
        assertThat(claims.get("lastName")).isEqualTo("test");
    }

    @Test(expected = TokenParserException.class)
    public void it_should_throw_exception_when_token_is_invalid() {
        // given
        String jwt = "invalidToken";

        // when
        jwtParserService.decodeJWT(jwt);
    }
}