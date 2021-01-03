package getir.bookretailfirm.exception;

public class TokenParserException extends RuntimeException {
    public TokenParserException(Exception e) {
        super("JWT Token could not parsed, ", e);
    }
}
