package br.com.fusion.banck.controller;

import br.com.fusion.banck.entity.FusionApiEntity;
import br.com.fusion.banck.producer.FusionBankApiRabbitProducer;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation. *;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fusion")
@RateLimiter(name = "api-limit", fallbackMethod = "")
public class FusionController {

    private final FusionBankApiRabbitProducer producer;

    public FusionController(FusionBankApiRabbitProducer producer) {
        this.producer = producer;
    }

// Campo de cadastro onde o usuário cria sua conta se não tiver.
    @PostMapping(consumes = "application/json",
            produces = "application/json",
            path = "/create-account"
    )
    public ResponseEntity<Object> registerUser(@RequestBody FusionApiEntity dadosUsuario) {
        producer.sendQueue(dadosUsuario);
        return ResponseEntity.ok().build();

    }
}
