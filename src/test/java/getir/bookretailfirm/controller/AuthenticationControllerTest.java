package getir.bookretailfirm.controller;

import getir.bookretailfirm.request.AuthenticationRequest;
import getir.bookretailfirm.response.AuthenticationResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_get_token() throws Exception {
        //given
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        given(authenticationService.getToken(any(AuthenticationRequest.class))).willReturn(authenticationResponse);

        //when

        ResultActions resultActions = mockMvc.perform(post("/auth")
                .contentType("application/json").content("{\n" +
                        "  \"email\": \"email@email.com\",\n" +
                        "  \"password\": \"password\"\n" +
                        "}"));

        //then

        resultActions.andExpect(status().isOk());

        ArgumentCaptor<AuthenticationRequest> requestCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        verify(authenticationService).getToken(requestCaptor.capture());
        AuthenticationRequest authenticationRequest = requestCaptor.getValue();
        assertThat(authenticationRequest.getEmail()).isEqualTo("email@email.com");
        assertThat(authenticationRequest.getPassword()).isEqualTo("password");
    }
}