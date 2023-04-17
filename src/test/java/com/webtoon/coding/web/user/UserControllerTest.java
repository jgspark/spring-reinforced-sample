package com.webtoon.coding.web.user;

import com.webtoon.coding.core.exception.RestExceptionHandler;
import com.webtoon.coding.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(new UserController(userService))
                        .setControllerAdvice(new RestExceptionHandler())
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .build();
    }

    @Test
    @DisplayName("유저 삭제 API")
    void removeUser() throws Exception {

        doNothing().when(userService).removeUser(any());

        ResultActions action =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.delete("/user/" + 1)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8"))
                        .andDo(print());

        verify(userService , times(1)).removeUser(any());

        action.andExpect(status().isOk());
    }
}
