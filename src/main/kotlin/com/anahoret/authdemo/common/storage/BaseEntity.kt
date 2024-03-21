package com.anahoret.authdemo.common.storage

import jakarta.persistence.*
import java.util.*

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null
)
