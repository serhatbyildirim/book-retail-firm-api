package getir.bookretailfirm.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import getir.bookretailfirm.domain.Book;
import getir.bookretailfirm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final CouchbaseTemplate couchbaseTemplate;

    public void save(Book book) {
        couchbaseTemplate.save(book);
    }

    public void update(Book book) {
        try {
            Bucket bucket = couchbaseTemplate.getCouchbaseBucket();
            JsonDocument lockedDocument = bucket.getAndLock(book.getBookId(), 10);
            lockedDocument.content().put("stock", book.getStock() - 1);
            bucket.replace(lockedDocument);
        } catch (Exception e) {
            log.error("Concurrency error occurred : ", e);
        }

    }

    public Book findById(String id) {
        Book book = couchbaseTemplate.findById(id, Book.class);
        if (Objects.isNull(book)) {
            throw new NotFoundException("Book is not found by id : " + id);
        }
        return book;
    }
}
