package getir.bookretailfirm.exception;

public class TokenGenerateException extends RuntimeException {

    public TokenGenerateException(Exception e) {
        super("JWT Token could not generated, ", e);
    }
}
