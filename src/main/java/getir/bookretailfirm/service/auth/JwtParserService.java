package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.TokenParserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtParserService {

    @Value("${security.token.secret.key}")
    private String secretKey;

    public Claims decodeJWT(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception ex) {
            throw new TokenParserException(ex);
        }
    }
}
