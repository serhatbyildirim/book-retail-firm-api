package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.request.BookSaveRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookConverterTest {

    @InjectMocks
    private BookConverter bookConverter;

    @Test
    public void it_should_convert() {
        // given
        BookSaveRequest bookSaveRequest = new BookSaveRequest();
        bookSaveRequest.setName("name");
        bookSaveRequest.setStock(10);

        // when
        Book book = bookConverter.apply(bookSaveRequest);

        // then
        assertThat(book.getName()).isEqualTo("name");
        assertThat(book.getStock()).isEqualTo(10);
    }
}