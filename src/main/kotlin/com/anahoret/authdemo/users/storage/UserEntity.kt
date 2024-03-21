package com.anahoret.authdemo.users.storage

import com.anahoret.authdemo.common.storage.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "user_name")
    var userName: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password_hash")
    var passwordHash: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_role_id", referencedColumnName = "id")]
    )
    var roles: Set<UserRoleEntity>
) : BaseEntity()
