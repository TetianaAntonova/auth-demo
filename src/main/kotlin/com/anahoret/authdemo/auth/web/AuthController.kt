package com.anahoret.authdemo.auth.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    companion object {
        const val AUTH_PATH = "/api/auth"
        const val SIGN_IN_PATH = "$AUTH_PATH/sign-in"
        const val SIGN_UP_PATH = "$AUTH_PATH/sign-up"
    }

    @GetMapping("/index")
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello world")
    }

//    @PostMapping(SIGN_IN_PATH)
//    fun auth(@RequestBody request: SignInRequest): ResponseEntity<*> {
//        // todo
//    }
//
//    @PostMapping(SIGN_UP_PATH)
//    fun auth(@RequestBody request: SignUpRequest): ResponseEntity<*> {
//        // todo
//    }

    data class SignInRequest(
        val userEmail: String,
        val password: String
    )

    data class SignUpRequest(
        val userEmail: String,
        val password: String
    )
}
