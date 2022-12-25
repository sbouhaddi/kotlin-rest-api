package com.sbouhaddi.kotlinapi.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sbouhaddi.kotlinapi.model.Message
import com.sbouhaddi.kotlinapi.service.MessageService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests @Autowired constructor(val restTemplate: TestRestTemplate, val service: MessageService) {

    lateinit var message: Message

    @BeforeAll
    fun setup() {
        println(">> Setup")
        message = Message(1, "Hello!")
        service.create(message)
    }

    @Test
    fun `Assert get all messages, content and status code`() {
        println(">> Assert get all messages, content and status code")

        val entity = restTemplate.getForEntity<String>("/api/v1/messages")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(message.text)
    }

    @Test
    fun `Assert get message by id, content and status code`() {
        println(">> Assert get all messages, content and status code")

        val entity = restTemplate.getForEntity<String>("/api/v1/messages/${message.id}")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(message.text)
    }


    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}