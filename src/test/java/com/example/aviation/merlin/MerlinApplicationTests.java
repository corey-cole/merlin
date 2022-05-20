package com.example.aviation.merlin;

import javax.crypto.Cipher;

import com.amazon.corretto.crypto.provider.AmazonCorrettoCryptoProvider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MerlinApplicationTests {

	@Autowired
	private HelloController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	void isUsingACCP() throws Exception {
		assertThat(Cipher.getInstance("AES/GCM/NoPadding").getProvider().getName().equals(AmazonCorrettoCryptoProvider.PROVIDER_NAME));
	}

}
