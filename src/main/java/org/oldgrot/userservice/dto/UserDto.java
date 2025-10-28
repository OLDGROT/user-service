package org.oldgrot.userservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "users")
@Schema(description = "Пользователь")
public class UserDto {
    @Schema(description = "id полльзователя", example = "1")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Дмитрий")
    private String username;
    @Schema(description = "email пользователя", example = "user@mail.ru")
    private String email;
    @Schema(description = "Возраст", format = "byte", example = "23")
    private byte age;
    @Schema(hidden = false)
    private String role;
    @Schema(
            description = "Ссылки на связанные ресурсы",
            example = "{\"all-users\"}"
    )
    private Map<String, String> links;

    public void setLinks(Map<String, String> stringStringMap) {
        links.putAll(stringStringMap);
    }
}
