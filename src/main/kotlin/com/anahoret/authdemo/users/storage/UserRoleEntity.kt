package com.anahoret.authdemo.users.storage

import com.anahoret.authdemo.common.storage.BaseEntity
import com.anahoret.authdemo.users.domain.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "user_roles")
class UserRoleEntity(
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    var roleName: UserRole
) : BaseEntity()
