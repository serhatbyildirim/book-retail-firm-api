package getir.bookretailfirm.controller.advice;

import getir.bookretailfirm.exception.AuthenticationFailedException;
import getir.bookretailfirm.exception.NotFoundException;
import getir.bookretailfirm.service.auth.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TestController.class)
public class GlobalControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_respond_generic_error_with_500_http_status() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/generic-exception"));

        //then
        resultActions
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.exception", is("GenericException")))
                .andExpect(jsonPath("$.errors[0]", is("Generic exception occurred.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_respond_with_400_for_AuthenticationFailedException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/authentication-failed-exception"));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("AuthenticationFailedException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_respond_with_404_for_NotFoundException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/not-found-exception"));

        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is("NotFoundException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_respond_with_401_for_AccessDeniedException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/access-denied-exception"));

        //then
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.exception", is("AccessDeniedException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_respond_with_401_for_MethodArgumentNotValidException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/method-argument-not-valid-exception"));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_respond_with_401_for_ConstraintViolationException() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get("/constraint-violation-exception"));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception", is("ConstraintViolationException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}

@RestController
class TestController {

    @GetMapping("/generic-exception")
    public void throwException() throws Exception {
        throw new Exception("generic exception occurred");
    }

    @GetMapping("/authentication-failed-exception")
    public void throwAuthenticationFailedException() {
        throw new AuthenticationFailedException("email");
    }

    @GetMapping("/not-found-exception")
    public void throwNotFoundException() {
        throw new NotFoundException("not found");
    }

    @GetMapping("/access-denied-exception")
    public void throwAccessDeniedException() {
        throw new AccessDeniedException("failed");
    }

    @GetMapping("/constraint-violation-exception")
    public void throwConstraintViolationException() {
        throw new ConstraintViolationException("failed", new HashSet<>());
    }

    @GetMapping("/method-argument-not-valid-exception")
    public void throwMethodArgumentNotValidException() throws MethodArgumentNotValidException {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("request", "fieldName", "message.key");
        given(bindingResult.getFieldErrors()).willReturn(Collections.singletonList(fieldError));
        throw new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult) {
            @Override
            public String getMessage() {
                return "method-argument-not-valid-exception";
            }
        };
    }
}