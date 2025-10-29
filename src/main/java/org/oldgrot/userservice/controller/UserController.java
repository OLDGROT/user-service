package org.oldgrot.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oldgrot.userservice.dto.user.UserDto;
import org.oldgrot.userservice.dto.user.ResponseUserDto;
import org.oldgrot.userservice.hateos.UserModelAssembler;
import org.oldgrot.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Операции с пользователями")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;

    @GetMapping("/getUsers")
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей получен")
    public ResponseEntity<List<ResponseUserDto>>getAll() {
        log.info("Получен запрос на получение всех пользователей");

        List<ResponseUserDto> users = userService.getAllUsers().stream()
                .peek(responseUserDto -> responseUserDto.setLinks(Map.of("Обновить пользователя", "/update/{id}","Удалить пользователя", "/delete/{id}")))
                .toList();

        return ResponseEntity.ok(users);

    }

    @PostMapping("/createUser")
    @Operation(summary = "Создать нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан")
    public ResponseEntity<ResponseUserDto> create(@RequestBody UserDto dto) {
        log.info("Создание пользователя с email: {}", dto.getEmail());
        ResponseUserDto created = userService.createUser(dto);

        created.setLinks(Map.of("Получить всех пользователей", "/api/users"));
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён")
    public ResponseEntity<ResponseUserDto> update(
            @PathVariable @Parameter(description = "id пользователя") Long id,
            @RequestBody UserDto dto) {

        log.info("Обновление пользователя с id: {}", id);
        ResponseUserDto updated = userService.updateUser(id, dto);

        updated.setLinks(Map.of("Получить всех пользователей", "/api/"));

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить пользователя")
    @ApiResponse(responseCode = "204", description = "Пользователь удалён")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "id пользователя") Long id) {
        log.info("Удаление пользователя с id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
