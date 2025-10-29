package org.oldgrot.userservice.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "users")
@Schema(description = "Возвращаемый пользователь")
public class ResponseUserDto{
    @Schema(description = "id полльзователя", example = "1")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Дмитрий")
    private String username;
    @Schema(description = "email пользователя", example = "user@mail.ru")
    private String email;
    @Schema(description = "Возраст",
            format = "byte",
            example = "23")
    @Min(1)
    @Max(128)
    private int age;
    @Schema(hidden = true)
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
