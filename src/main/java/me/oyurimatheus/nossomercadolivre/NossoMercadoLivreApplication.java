package me.oyurimatheus.nossomercadolivre;

import me.oyurimatheus.nossomercadolivre.users.Password;
import me.oyurimatheus.nossomercadolivre.users.User;
import me.oyurimatheus.nossomercadolivre.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NossoMercadoLivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(NossoMercadoLivreApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository repository) {
		User buzz = new User("buzz@toystory.com", Password.encode("123456"));
		User woody = new User("woody@toystory.com", Password.encode("123456"));

		return (args) -> {
			repository.save(buzz);
			repository.save(woody);
		};

	}
}
