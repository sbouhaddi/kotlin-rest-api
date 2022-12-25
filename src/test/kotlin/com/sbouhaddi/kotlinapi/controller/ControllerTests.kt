package com.sbouhaddi.kotlinapi.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sbouhaddi.kotlinapi.model.Message
import com.sbouhaddi.kotlinapi.service.MessageService
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ExtendWith(SpringExtension::class)
class ControllerTests {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun service() = mockk<MessageService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var service: MessageService
    private lateinit var m1: Message

    @BeforeAll
    fun setup() {
        println(">> Setup")
        m1 = Message(1, "Hello!")
    }


    @Test
    fun `List messages`() {
        val m2 = Message(2, "Kotlin")
        every { service.findMessages() } returns listOf(m1, m2)
        mockMvc.perform(get("/api/v1/messages").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].text").value(m1.text))
            .andExpect(jsonPath("\$.[1].text").value(m2.text))
    }

    @Test
    fun `Save message`() {
        val mapper = jacksonObjectMapper()
        every { service.create(m1) } returns m1
        mockMvc.perform(
            post("/api/v1/messages").content(mapper.writeValueAsString(m1))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun `Delete message`() {
        justRun { service.remove(1) }
        mockMvc.perform(delete("/api/v1/messages/{id}", 1))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `Update message`() {
        val mapper = jacksonObjectMapper()
        val m2 = Message(1, "Kotlin")
        every { service.update(1, m2) } returns m2
        mockMvc.perform(
            put("/api/v1/messages/{id}", 1).content(mapper.writeValueAsString(m2))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}