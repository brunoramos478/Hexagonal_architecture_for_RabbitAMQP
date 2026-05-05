package br.com.fusion.banck.adapter.in.web;

import java.util.List;
import br.com.fusion.banck.shared.dto.UserDto;
import br.com.fusion.banck.shared.exceptions.ResponseSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> fallbackUser(UserDto dadosUsuario, RequestNotPermitted exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Você chegou ao limite. Tente novamente mais tarde. "
        + exception.getMessage());
    }

    @RateLimiter(name = "api-limit", fallbackMethod = "fallbackUser")
    @PostMapping(consumes = "application/json",
            produces = "application/json",
            path = "/create-account"
    )
    // Campo de cadastro onde o usuário cria sua conta se não tiver.
    public ResponseEntity registerUser(@RequestBody UserDto dadosUsuario) {

        dadosUsuario.validate(dadosUsuario);
        producer.sendQueue("fusion.exchange", "fusion.routing.key", dadosUsuario);
        return ResponseEntity.ok(ResponseSuccess.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Usuário cadastrado com sucesso!")
                .timestamp(System.currentTimeMillis())
                .data(dadosUsuario)
                .build());
    }

    @GetMapping("/products")
    public ResponseEntity<List<String>> getProducts(@RequestBody UserDto dadosUsuario) {
        producer.sendQueue("fusion.products.exchange", "fusion.products.routing.key", dadosUsuario);
        return ResponseEntity.ok(List.of("Produto 1", "Produto 2", "Produto 3"));
    }
}