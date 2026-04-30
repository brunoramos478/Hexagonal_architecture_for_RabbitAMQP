package br.com.fusion.banck.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fusion.banck.domain.entity.FusionApiEntity;
import br.com.fusion.banck.adapter.out.messaging.FusionBankApiRabbitProducer;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;


@RestController
@RequestMapping("/fusion")
public class FusionController {

    private final FusionBankApiRabbitProducer producer;

    public FusionController(FusionBankApiRabbitProducer producer) {
        this.producer = producer;
    }

    // Metodo fallback.
    public ResponseEntity<String> fallbackUser(FusionApiEntity dadosUsuario, RequestNotPermitted exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Você chegou ao limite. Tente novamente mais tarde. "
        + exception.getMessage());
    }

    @RateLimiter(name = "api-limit", fallbackMethod = "fallbackUser")
    @PostMapping(consumes = "application/json",
            produces = "application/json",
            path = "/create-account"
    )
    // Campo de cadastro onde o usuário cria sua conta se não tiver.
    public ResponseEntity<String> registerUser(@RequestBody FusionApiEntity dadosUsuario) {

        producer.sendQueue("fusion.exchange", "fusion.routing.key", dadosUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");

    }
    @GetMapping("/products")
    public ResponseEntity<List<String>> getProducts(@RequestBody FusionApiEntity dadosUsuario) {
        producer.sendQueue("fusion.products.exchange", "fusion.products.routing.key", dadosUsuario);
        return ResponseEntity.ok(List.of("Produto 1", "Produto 2", "Produto 3"));


    }
}
