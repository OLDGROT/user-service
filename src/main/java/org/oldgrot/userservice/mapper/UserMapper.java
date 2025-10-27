package org.oldgrot.userservice.mapper;

import org.oldgrot.userservice.dto.UserDto;
import org.oldgrot.userservice.model.Role;
import org.oldgrot.userservice.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "role")
    UserDto toDto(User user);
    @Mapping(source = "role", target = "role")
    User toEntity(UserDto userDto,@Context Role role);

    default Role map(String roleName) {
        if (roleName == null) return null;
        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
