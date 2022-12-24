package com.sbouhaddi.kotlinapi

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import com.sbouhaddi.kotlinapi.model.Message


@RestController
class MessageResource {

	@GetMapping("/")
	fun index(): List<Message> = listOf(
		Message("1", "Hello "),
		Message("2", "World !"),
		Message("3", "Kotlin ")
	)
}