package getir.bookretailfirm.controller;

import getir.bookretailfirm.request.BookSaveRequest;
import getir.bookretailfirm.response.BookResponse;
import getir.bookretailfirm.service.BookService;
import getir.bookretailfirm.service.auth.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_save() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType("application/json").content("{\n" +
                        "  \"name\": \"bookName\",\n" +
                        "  \"stock\": 10\n" +
                        "}"));

        // then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<BookSaveRequest> saveCaptor = ArgumentCaptor.forClass(BookSaveRequest.class);
        verify(bookService).saveBook(saveCaptor.capture());
        BookSaveRequest bookSaveRequest = saveCaptor.getValue();
        assertThat(bookSaveRequest.getName()).isEqualTo("bookName");
        assertThat(bookSaveRequest.getStock()).isEqualTo(10);
    }

    @Test
    public void it_should_return_detail() throws Exception {
        // given
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId("id");
        bookResponse.setName("name");
        bookResponse.setStock(10);
        given(bookService.getBookDetail("id")).willReturn(bookResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book/id"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id")))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.stock", is(10)));


    }
}