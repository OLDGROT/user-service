package org.oldgrot.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.oldgrot.userservice.controller.UserController;
import org.oldgrot.userservice.dto.user.UserDto;
import org.oldgrot.userservice.dto.user.ResponseUserDto;
import org.oldgrot.userservice.hateos.UserModelAssembler;
import org.oldgrot.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserModelAssembler userModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllUsers_returnsListOfUsers() throws Exception {
        List<ResponseUserDto> users = List.of(
                new ResponseUserDto(1L, "Alice", "alice@mail.com", (byte) 23, "ADMIN", null),
                new ResponseUserDto(2L, "Bob", "bob@mail.com", (byte) 24, "USER", null)
        );

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$._embedded.users[0].username").value("Alice"))
                .andExpect(jsonPath("$._embedded.users[0].role").value("ADMIN"));
    }

    @Test
    void createUser_returnsCreatedUser() throws Exception {
       UserDto request = new UserDto(2L, "Charlie", "charlie@mail.com", 22, "ADMIN");
        ResponseUserDto saved = new ResponseUserDto(3L, "Charlie", "charlie@mail.com", (byte) 22, "USER", null);

        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.username").value("Charlie"));
    }

    @Test
    void updateUser_returnsUpdatedUser() throws Exception {
        ResponseUserDto request = new ResponseUserDto(null, "AliceUpdated", "alice_updated@mail.com", (byte) 23, "ADMIN", null);
        ResponseUserDto updated = new ResponseUserDto(1L, "AliceUpdated", "alice_updated@mail.com", (byte) 23, "USER", null);

        Mockito.when(userService.updateUser(eq(1L), any(ResponseUserDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("AliceUpdated"));
    }

    @Test
    void deleteUser_returnsNoContent() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

}
