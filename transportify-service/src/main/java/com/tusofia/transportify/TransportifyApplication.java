package com.tusofia.transportify;

import com.tusofia.transportify.file.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties({
				FileStorageProperties.class
})
public class TransportifyApplication {

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/**
	 * Initialize spring boot application.
	 *
	 * @param args main parameters
	 */
	public static void main(String... args) {
		TransportifyApplication application = new TransportifyApplication();
		application.launch(args);
	}

	/**
	 * Used to silence pmd error about only static methods in the class.
	 *
	 * @param args main parameters
	 */
	private void launch(String... args) {
		SpringApplication.run(TransportifyApplication.class, args);
	}
}
