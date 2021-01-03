package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.EncryptionException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class EncryptionService {
    public String encrypt(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] value = password.getBytes(StandardCharsets.UTF_16LE);
            byte[] a = m.digest(value);
            return Base64.getEncoder().encodeToString(a);
        } catch (Exception ex) {
            throw new EncryptionException(ex);
        }
    }
}
