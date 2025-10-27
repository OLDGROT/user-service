package org.oldgrot.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oldgrot.userservice.dto.UserDto;
import org.oldgrot.userservice.hateos.UserModelAssembler;
import org.oldgrot.userservice.kafka.UserEventerKafka;
import org.oldgrot.userservice.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Операции с пользователями")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler assembler;
    private final UserEventerKafka producer;

    @GetMapping(name = "/getUsers")
    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей получен")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAll() {
        log.info("Получен запрос на получение всех пользователей");

        List<EntityModel<UserDto>> users = userService.getAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getAll()).withSelfRel()))
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).getAll()).withSelfRel())
        );
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан")
    public ResponseEntity<EntityModel<UserDto>> create(@RequestBody UserDto dto) {
        log.info("Создание пользователя с email: {}", dto.getEmail());
        UserDto created = userService.createUser(dto);
        producer.sendUserCreate(dto.getId());

        EntityModel<UserDto> model = EntityModel.of(
                created,
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
        );

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён")
    public ResponseEntity<EntityModel<UserDto>> update(
            @PathVariable Long id,
            @RequestBody UserDto dto) {

        log.info("Обновление пользователя с id: {}", id);
        UserDto updated = userService.updateUser(id, dto);

        EntityModel<UserDto> model = EntityModel.of(
                updated,
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
        );

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    @ApiResponse(responseCode = "204", description = "Пользователь удалён")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Удаление пользователя с id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
