package org.radhe.saas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RegisterUserApplication

fun main(args: Array<String>) {
	runApplication<RegisterUserApplication>(*args)
}
