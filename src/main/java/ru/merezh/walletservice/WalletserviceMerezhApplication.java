package ru.merezh.walletservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Walletservice Merezh",
                version = "v1.0",
                description = "Эндпоинты для взаимодействия с балансом пользователя"
        )
)
@SpringBootApplication
public class WalletserviceMerezhApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletserviceMerezhApplication.class, args);
	}

}
