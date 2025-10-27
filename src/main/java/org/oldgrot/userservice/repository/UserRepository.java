package org.oldgrot.userservice.repository;


import org.oldgrot.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}