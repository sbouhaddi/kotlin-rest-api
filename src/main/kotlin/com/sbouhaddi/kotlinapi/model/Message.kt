package com.sbouhaddi.kotlinapi.model


import jakarta.persistence.*

@Entity
@Table(name = "MESSAGES")
data class Message(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id:Long, val text: String)