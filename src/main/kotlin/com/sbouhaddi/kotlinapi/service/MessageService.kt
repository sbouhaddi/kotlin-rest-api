package com.sbouhaddi.kotlinapi.service

import org.springframework.stereotype.Service
import com.sbouhaddi.kotlinapi.dao.MessageRepository
import com.sbouhaddi.kotlinapi.model.Message
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
class MessageService(val db: MessageRepository) {

    fun findMessages(): List<Message> = db.findAll()
    fun getById(id: Long): Message = db.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(message: Message): Message = db.save(message)

    fun remove(id: Long) {
        if (db.existsById(id)) db.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun update(id: Long, message: Message): Message {
        return if (db.existsById(id)) {
            message.id = id
            db.save(message)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}