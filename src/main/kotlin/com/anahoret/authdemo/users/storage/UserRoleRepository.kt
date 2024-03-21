package com.anahoret.authdemo.users.storage

import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRoleEntity, Long>
