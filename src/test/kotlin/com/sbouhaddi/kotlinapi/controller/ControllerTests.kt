package com.sbouhaddi.kotlinapi.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sbouhaddi.kotlinapi.dao.MessageRepository
import com.sbouhaddi.kotlinapi.model.Message
import com.sbouhaddi.kotlinapi.service.MessageService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ControllerTests(@Autowired val mockMvc: MockMvc) {
    @MockBean
    lateinit var messageService: MessageService

    @MockBean
    lateinit var messageRepo: MessageRepository

    @Test
    fun `List messages`() {
        val m1 = Message(1, "Hello")
        val m2 = Message(2, "Kotlin")
        Mockito.`when`(messageService.findMessages()).thenReturn(listOf(m1, m2))
        mockMvc.perform(get("/api/v1/messages").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].text").value(m1.text))
            .andExpect(jsonPath("\$.[1].text").value(m2.text))
    }

    @Test
    fun `Save message`() {
        val mapper = jacksonObjectMapper()
        mockMvc.perform(
            post("/api/v1/messages").content(mapper.writeValueAsString(Message(1, "Hello")))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun `Delete message`() {
        mockMvc.perform(delete("/api/v1/messages/{id}", 1))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `Update message`() {
        val mapper = jacksonObjectMapper()
        mockMvc.perform(
            put("/api/v1/messages/{id}", 1).content(mapper.writeValueAsString(Message(1, "Kotlin")))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

}