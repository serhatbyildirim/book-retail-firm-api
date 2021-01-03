package getir.bookretailfirm.converter;

import getir.bookretailfirm.model.IdentityUser;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class IdentityUserConverter {
    public IdentityUser apply(Claims claims) {
        IdentityUser identityUser = new IdentityUser();
        identityUser.setCustomerId(getValue(claims, "customerId"));
        identityUser.setEmail(getValue(claims, "email"));
        identityUser.setFirstName(getValue(claims, "firstName"));
        identityUser.setLastName(getValue(claims, "lastName"));
        return identityUser;
    }

    private String getValue(Claims claims, String key) {
        Object foundValue = claims.getOrDefault(key, StringUtils.EMPTY);
        return foundValue == null ? null : foundValue.toString();
    }
}