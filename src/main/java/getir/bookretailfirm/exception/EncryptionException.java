package getir.bookretailfirm.exception;

public class EncryptionException extends  RuntimeException {

    public EncryptionException(Exception ex) {
        super("Password couldn't encrypted, ", ex);
    }
}
