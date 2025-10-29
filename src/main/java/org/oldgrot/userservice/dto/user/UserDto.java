package org.oldgrot.userservice.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "users")
@Schema(description = "Получаемый пользователь")
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private String role;
}
