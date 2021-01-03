package getir.bookretailfirm.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterRequest {

    @Email(message = "request.invalid.email")
    private String email;
    @NotBlank(message = "request.invalid.password")
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}