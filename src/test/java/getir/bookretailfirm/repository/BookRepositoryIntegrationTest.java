package getir.bookretailfirm.repository;

import getir.bookretailfirm.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private CouchbaseTemplate couchbaseTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void it_should_save_and_find_by_id() {
        // given
        Book objectToSave = new Book("bookId", "bookName", 10);

        // when
        bookRepository.save(objectToSave);

        // then
        Book bookDocument = couchbaseTemplate.findById("bookId", Book.class);
        assertThat(bookDocument.getBookId()).isEqualTo("bookId");
        assertThat(bookDocument.getName()).isEqualTo("bookName");
        assertThat(bookDocument.getStock()).isEqualTo(10);

        couchbaseTemplate.remove(bookDocument);
    }

    @Test
    public void it_should_update() {
        // given
        Book objectToSave = new Book("bookId", "bookName", 10);
        bookRepository.save(objectToSave);

        // when
        bookRepository.update(objectToSave);

        // then
        Book bookDocument = couchbaseTemplate.findById("bookId", Book.class);
        assertThat(bookDocument.getBookId()).isEqualTo("bookId");
        assertThat(bookDocument.getName()).isEqualTo("bookName");
        assertThat(bookDocument.getStock()).isEqualTo(9);

        couchbaseTemplate.remove(bookDocument);
    }

}