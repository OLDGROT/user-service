package org.oldgrot.userservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "users")
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private byte age;
    private String role;
}
