package org.oldgrot.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.mapstruct.Mapper;


import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "users")
@Mapper(componentModel = "spring")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sq")
    @SequenceGenerator(name = "user_sq", sequenceName = "user_sequence", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private byte age;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

}