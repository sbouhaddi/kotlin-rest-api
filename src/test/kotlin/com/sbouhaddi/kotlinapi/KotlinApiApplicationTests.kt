package com.sbouhaddi.kotlinapi

import com.sbouhaddi.kotlinapi.controller.MessageResource
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class KotlinApiApplicationTests {

	@Autowired
	lateinit var controller: MessageResource
	@Test
	fun contextLoads() {
		assertThat(controller).isNotNull
	}

}
