package com.sbouhaddi.kotlinapi.controller

import com.sbouhaddi.kotlinapi.dao.MessageRepository
import com.sbouhaddi.kotlinapi.model.Message
import com.sbouhaddi.kotlinapi.service.MessageService
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ControllerTests(@Autowired val mockMvc: MockMvc) {
    @MockBean
    lateinit var messageService: MessageService

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
}