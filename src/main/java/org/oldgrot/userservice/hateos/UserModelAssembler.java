package org.oldgrot.userservice.hateos;

import org.oldgrot.userservice.controller.UserController;
import org.oldgrot.userservice.dto.user.ResponseUserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<ResponseUserDto, EntityModel<ResponseUserDto>> {

    @Override
    public EntityModel<ResponseUserDto> toModel(ResponseUserDto user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users"),
                linkTo(methodOn(UserController.class).update(user.getId(), null)).withRel("update-user"),
                linkTo(methodOn(UserController.class).delete(user.getId())).withRel("delete-user"));
    }
}
