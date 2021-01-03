package getir.bookretailfirm.controller;

import getir.bookretailfirm.request.RegisterRequest;
import getir.bookretailfirm.service.CustomerService;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_save() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(post("/customer")
                .contentType("application/json").content("{\n" +
                        "  \"email\": \"email@email.com\",\n" +
                        "  \"password\": \"password\",\n" +
                        "  \"firstName\": \"firstName\",\n" +
                        "  \"lastName\": \"lastName\"\n" +
                        "}"));

        // then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<RegisterRequest> saveCaptor = ArgumentCaptor.forClass(RegisterRequest.class);
        verify(customerService).saveCustomer(saveCaptor.capture());
        RegisterRequest registerRequest = saveCaptor.getValue();
        assertThat(registerRequest.getEmail()).isEqualTo("email@email.com");
        assertThat(registerRequest.getPassword()).isEqualTo("password");
        assertThat(registerRequest.getFirstName()).isEqualTo("firstName");
        assertThat(registerRequest.getLastName()).isEqualTo("lastName");
    }

}