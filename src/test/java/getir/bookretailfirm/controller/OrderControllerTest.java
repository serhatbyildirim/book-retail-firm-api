package getir.bookretailfirm.controller;

import getir.bookretailfirm.enums.OrderStatus;
import getir.bookretailfirm.model.IdentityUser;
import getir.bookretailfirm.model.UserAuthentication;
import getir.bookretailfirm.response.OrderResponse;
import getir.bookretailfirm.service.OrderService;
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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_create_order() throws Exception {
        // given
        IdentityUser customer = new IdentityUser();
        customer.setCustomerId("customerId");
        customer.setEmail("email");
        given(authenticationService.getUserAuthentication(any())).willReturn(new UserAuthentication(customer));

        // when
        ResultActions resultActions = mockMvc.perform(post("/orders")
                .param("bookId", "id"));

        // then
        resultActions.andExpect(status().isCreated());
        ArgumentCaptor<String> saveCaptor = ArgumentCaptor.forClass(String.class);
        verify(orderService).createOrder(eq("customerId"), saveCaptor.capture());
        String bookId = saveCaptor.getValue();
        assertThat(bookId).isEqualTo("id");
    }

    @Test
    public void it_should_get_order_detail() throws Exception {
        // given
        IdentityUser customer = new IdentityUser();
        customer.setCustomerId("customerId");
        customer.setEmail("email");
        given(authenticationService.getUserAuthentication(any())).willReturn(new UserAuthentication(customer));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId("orderId");
        orderResponse.setBookId("bookId");
        orderResponse.setCustomerId("customerId");
        orderResponse.setStatus(OrderStatus.NEW_ORDER);
        given(orderService.getOrderDetail("customerId", "id")).willReturn(orderResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/orders/id"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("orderId"))
                .andExpect(jsonPath("$.bookId").value("bookId"))
                .andExpect(jsonPath("$.customerId").value("customerId"));

        ArgumentCaptor<String> saveCaptor = ArgumentCaptor.forClass(String.class);
        verify(orderService).getOrderDetail(eq("customerId"), saveCaptor.capture());
        String orderId = saveCaptor.getValue();
        assertThat(orderId).isEqualTo("id");
    }

    @Test
    public void it_should_get_order_list() throws Exception {
        // given
        IdentityUser customer = new IdentityUser();
        customer.setCustomerId("customerId");
        customer.setEmail("email");
        given(authenticationService.getUserAuthentication(any())).willReturn(new UserAuthentication(customer));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId("orderId");
        orderResponse.setBookId("bookId");
        orderResponse.setCustomerId("customerId");
        orderResponse.setStatus(OrderStatus.NEW_ORDER);
        given(orderService.getOrderList("customerId")).willReturn(Collections.singletonList(orderResponse));

        // when
        ResultActions resultActions = mockMvc.perform(get("/orders"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].orderId").value("orderId"))
                .andExpect(jsonPath("$.[0].bookId").value("bookId"))
                .andExpect(jsonPath("$.[0].customerId").value("customerId"));

        verify(orderService).getOrderList("customerId");
    }
}