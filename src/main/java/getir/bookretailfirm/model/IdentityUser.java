package getir.bookretailfirm.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class IdentityUser implements Serializable {

    private static final long serialVersionUID = 8501074510102585283L;

    private String customerId;
    private String email;
    private String firstName;
    private String lastName;
}
