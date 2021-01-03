package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.AuthenticationFailedException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class TokenService {

    private final JwtParserService jwtParserService;

    public TokenService(JwtParserService jwtParserService) {
        this.jwtParserService = jwtParserService;
    }

    public Claims getTokenClaims(HttpServletRequest httpServletRequest) {
        Claims claims = jwtParserService.decodeJWT(getTokenFromHeader(httpServletRequest));
        validateToken(claims);
        return claims;
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        String authenticationHeader = httpServletRequest.getHeader("Authorization");
        boolean startWithBearer = StringUtils.startsWith(authenticationHeader, "Bearer");
        String[] headerParams = StringUtils.split(authenticationHeader, StringUtils.SPACE);
        boolean headerParamSizeIsTwo = ArrayUtils.getLength(headerParams) == 2;
        boolean isAuthenticationHeaderValid = authenticationHeader != null && startWithBearer && headerParamSizeIsTwo;
        if (!isAuthenticationHeaderValid) {
            throw new AuthenticationFailedException("Authentication header not valid");
        }
        return authenticationHeader.split(StringUtils.SPACE)[1];
    }

    private void validateToken(Claims tokenData) {
        if (DateTime.now().isAfter(tokenData.getExpiration().getTime())) {
            throw new AuthenticationFailedException("Token expired");
        }
    }
}
