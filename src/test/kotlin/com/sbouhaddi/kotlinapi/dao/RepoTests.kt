package com.sbouhaddi.kotlinapi.dao

import com.sbouhaddi.kotlinapi.model.Message
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepoTests(
    @Autowired
    val messageRepository: MessageRepository
) {

    lateinit var message: Message
    @BeforeAll
    fun init(){
        message = Message(1, "Hello!")
        messageRepository.save(message)
    }

    @AfterAll
    fun destroy(){
        messageRepository.deleteAll()
    }

    @Test
    fun `When findByIdOrNull then return Message`() {
        val found = messageRepository.findByIdOrNull(message.id!!)
        assertThat(found).isEqualTo(message)
    }

    @Test
    fun `When findMessages then return All Messages`() {
        val found = messageRepository.findAll()
        assertThat(found).isNotEmpty
        assertThat(found.size).isEqualTo(1)
    }

    @Test
    fun `When createMessage then return All Messages`() {
        val newMessage = Message(2, "Kotlin!")
        messageRepository.save(newMessage)

        val found = messageRepository.findAll()
        assertThat(found).isNotEmpty
        assertThat(found.size).isEqualTo(2)
    }
}