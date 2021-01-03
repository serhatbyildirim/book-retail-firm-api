package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.BookConverter;
import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.exception.NotFoundException;
import getir.bookretailfirm.repository.BookRepository;
import getir.bookretailfirm.request.BookSaveRequest;
import getir.bookretailfirm.response.BookResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookConverter bookConverter;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void it_should_save_book() {
        // given
        BookSaveRequest bookSaveRequest = new BookSaveRequest();
        bookSaveRequest.setName("bookName");
        bookSaveRequest.setStock(10);

        Book book = new Book("id", "name", 10);

        given(bookConverter.apply(bookSaveRequest)).willReturn(book);

        // when
        bookService.saveBook(bookSaveRequest);

        // then
        verify(bookRepository).save(book);
    }

    @Test
    public void it_should_get_detail() {
        // given
        Book book = new Book("id", "name", 10);

        given(bookRepository.findById("id")).willReturn(book);

        // when
        BookResponse response = bookService.getBookDetail("id");

        // then
        assertThat(response.getId()).isEqualTo("id");
        assertThat(response.getName()).isEqualTo("name");
        assertThat(response.getStock()).isEqualTo(10);
    }
}