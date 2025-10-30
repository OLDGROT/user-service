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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Операции с пользователями")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей получен")
    public ResponseEntity<CollectionModel<EntityModel<ResponseUserDto>>> getAll() {
        log.info("Получен запрос на получение всех пользователей");

        List<EntityModel<ResponseUserDto>> users = userService.getAllUsers()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<ResponseUserDto>> model = CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан")
    public ResponseEntity<EntityModel<ResponseUserDto>> create(@RequestBody UserDto dto) {
        log.info("Создание пользователя с email: {}", dto.getEmail());
        ResponseUserDto created = userService.createUser(dto);

        EntityModel<ResponseUserDto> model = assembler.toModel(created);

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён")
    public ResponseEntity<EntityModel<ResponseUserDto>> update(
            @PathVariable @Parameter(description = "id пользователя") Long id,
            @RequestBody UserDto dto) {

        log.info("Обновление пользователя с id: {}", id);
        ResponseUserDto updated = userService.updateUser(id, dto);
        EntityModel<ResponseUserDto> model = assembler.toModel(updated);

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    @ApiResponse(responseCode = "204", description = "Пользователь удалён")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "id пользователя") Long id) {
        log.info("Удаление пользователя с id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
