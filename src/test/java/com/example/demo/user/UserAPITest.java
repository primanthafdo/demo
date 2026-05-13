package com.example.demo.user;

import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void TEST_1_shouldReturn200AndSuccessMessageWhenNameStartsWithAtoMAndFirstLetterIsUppercase() throws Exception {
        Mockito.when(userService.getName("Alice")).thenReturn(UserService.INFO_MESSAGE_HELLO_ALICE);

        mockMvc.perform(get("/api/assignment/hello-world/Alice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UserService.INFO_MESSAGE_HELLO_ALICE));
    }


    @Test
    void Test_2_ShouldReturn200AndSuccessMessageWhenNameStartsWithAtoMAndLowercase() throws Exception {
        Mockito.when(userService.getName("alice")).thenReturn(UserService.INFO_MESSAGE_HELLO_ALICE);

        mockMvc.perform(get("/api/assignment/hello-world/alice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UserService.INFO_MESSAGE_HELLO_ALICE));
    }

    @Test
    void Test_3_ShouldReturn200AndSuccessMessageWhenNameStartsWithAtoMAndUppercase() throws Exception {
        Mockito.when(userService.getName("ALICE")).thenReturn(UserService.INFO_MESSAGE_HELLO_ALICE);

        mockMvc.perform(get("/api/assignment/hello-world/ALICE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(UserService.INFO_MESSAGE_HELLO_ALICE));
    }

    @Test
    void Test_4_ShouldReturn400AndErrorMessageWhenNameStartsWithNtoZ() throws Exception {
        Mockito.when(userService.getName("Nick")).thenThrow(new IllegalArgumentException(UserService.ERROR_MESSAGE_INVALID_INPUT));

        mockMvc.perform(get("/api/assignment/hello-world/Nick")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(UserService.ERROR_MESSAGE_INVALID_INPUT));
    }

    @Test
    void Test_5_ShouldReturn404ForEmptyPathVariable() throws Exception {
        mockMvc.perform(get("/api/assignment/hello-world/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
