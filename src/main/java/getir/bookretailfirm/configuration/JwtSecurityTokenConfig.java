package getir.bookretailfirm.configuration;

import getir.bookretailfirm.exception.TokenGenerateException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@ConfigurationProperties("security.token.secret")
public class JwtSecurityTokenConfig {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SecretKeySpec getSecretKeySpec() {
        try {
            return new SecretKeySpec(getKey().getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception ex) {
            log.error("Exception occurred : ", ex);
            throw new TokenGenerateException(ex);
        }
    }
}
