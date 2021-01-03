package getir.bookretailfirm.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookSaveRequest {

    @NotBlank(message = "bookRequest.name.notBlank")
    private String name;

    @Size(message = "bookRequest.stock.size")
    private Integer stock;
}
