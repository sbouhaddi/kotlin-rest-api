package com.sbouhaddi.kotlinapi.controller

import com.sbouhaddi.kotlinapi.model.Message
import com.sbouhaddi.kotlinapi.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/messages")
class MessageResource(val service: MessageService) {

    @GetMapping
    fun getAllMessages(): List<Message> = service.findMessages()

    @GetMapping("/{id}")
    fun getMessage(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveMessage(@RequestBody message: Message): Message = service.create(message)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMessage(@PathVariable id: Long) = service.remove(id)

    @PutMapping("/{id}")
    fun updateMessage(
            @PathVariable id: Long, @RequestBody message: Message
    ) = service.update(id, message)
}