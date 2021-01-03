package getir.bookretailfirm.service;

import getir.bookretailfirm.converter.BookConverter;
import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.repository.BookRepository;
import getir.bookretailfirm.request.BookSaveRequest;
import getir.bookretailfirm.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookConverter bookConverter;
    private final BookRepository bookRepository;

    public void saveBook(BookSaveRequest saveRequest) {
        Book book = bookConverter.apply(saveRequest);
        bookRepository.save(book);
        log.info("New Book Saved. BookId : {} , BookName : {}, Stock Count : {}", book.getBookId(), book.getName(), book.getStock());
    }

    public BookResponse getBookDetail(String id) {
        Book book = bookRepository.findById(id);
        return new BookResponse(book.getBookId(), book.getName(), book.getStock());
    }
}
