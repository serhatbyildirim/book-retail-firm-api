package getir.bookretailfirm.converter;

import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.request.BookSaveRequest;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public Book apply(BookSaveRequest saveRequest) {
        Book book = new Book();
        book.setName(saveRequest.getName());
        book.setStock(saveRequest.getStock());
        return book;
    }
}
