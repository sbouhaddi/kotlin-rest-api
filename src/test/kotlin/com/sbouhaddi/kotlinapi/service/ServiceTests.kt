package com.sbouhaddi.kotlinapi.service

import com.sbouhaddi.kotlinapi.dao.MessageRepository
import com.sbouhaddi.kotlinapi.model.Message
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.h2.util.MathUtils
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.web.server.ResponseStatusException


class ServiceTests() {

    private val messageRepo: MessageRepository = mockk()
    private val service = MessageService(messageRepo)

    @Test
    fun `remove message`() {
        every { messageRepo.existsById(1) } returns true
        justRun { messageRepo.deleteById(1) }
        every { messageRepo.findAll() } returns emptyList()
        service.remove(1)
        assertThat(service.findMessages()).isEmpty()
    }

    @Test
    fun `update message`() {
        val newMessage = Message(2, "Kotlin")
        every { messageRepo.existsById(2) } returns true
        every { messageRepo.save(newMessage) } returns newMessage
        service.update(2, newMessage)
        assertThat(service.findMessages()).isEmpty()
    }

    @Test
    fun `remove message exception`() {
        val block: () -> Unit = {
            every { messageRepo.existsById(1) } returns false
            service.remove(1)
        }
        assertThrows<ResponseStatusException>(block);
    }

    @Test
    fun `update message exception`() {
        val newMessage = Message(2, "Kotlin")
        val block: () -> Unit = {
            every { messageRepo.existsById(1) } returns false
            service.update(1, newMessage)
        }
        assertThrows<ResponseStatusException>(block);
    }

}