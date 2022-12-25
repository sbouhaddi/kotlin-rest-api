package com.sbouhaddi.kotlinapi.dao

import com.sbouhaddi.kotlinapi.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
}