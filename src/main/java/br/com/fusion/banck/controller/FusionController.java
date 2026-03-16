package br.com.fusion.banck.controller;

import br.com.fusion.banck.entity.FusionApiEntity;
import br.com.fusion.banck.producer.FusionApiResponse;
import br.com.fusion.banck.producer.FusionBankApiRabbitProducer;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation. *;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fusion")
public class FusionController {

    private final FusionBankApiRabbitProducer producer;

    public FusionController(FusionBankApiRabbitProducer producer) {
        this.producer = producer;
    }

// Campo de cadastro onde o usuário cria sua conta se não tiver.
    @RateLimiter(name = "api-limit", fallbackMethod = "fallbackUser")
    @PostMapping(consumes = "application/json",
            produces = "application/json",
            path = "/create-account"
    )
    public ResponseEntity<String> registerUser(@RequestBody FusionApiEntity dadosUsuario) {
        producer.sendQueue(dadosUsuario);
        FusionApiResponse response = new FusionApiResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");

    }
    public ResponseEntity<String> fallbackUser(FusionApiEntity dadosUsuario, RequestNotPermitted exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Você chegou ao limite. Tente novamente mais tarde.");
    }
}
