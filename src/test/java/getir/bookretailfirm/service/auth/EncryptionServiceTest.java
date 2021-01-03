package getir.bookretailfirm.service.auth;

import getir.bookretailfirm.exception.EncryptionException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(MockitoJUnitRunner.class)
public class EncryptionServiceTest {
    @InjectMocks
    private EncryptionService encryptionService;

    @Test
    public void it_should_encrypt_password() {
        //given

        //when
        String encryptPassword = encryptionService.encrypt("password");

        //then
        assertThat(encryptPassword).isEqualTo("sIHb6F4ew//D1OfQInQAzQ==");
    }

    @Test
    public void it_should_throw_exception() {
        //given

        //when
        Throwable throwable = catchThrowable(() -> encryptionService.encrypt(null));

        // Then
        AssertionsForClassTypes.assertThat(throwable).isInstanceOf(EncryptionException.class);
    }
}