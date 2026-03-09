package br.com.fusion.banck.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation. *;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fusion")
@RateLimiter(name = "api-limit", fallbackMethod = "fallback")

public class FusionController {

    private RabbitTemplate rabbitTemplate = new RabbitTemplate();

    public FusionController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


// Campo de cadastro onde o usuário cria sua conta se não tiver.
    @PostMapping("/create-account")
    public ResponseEntity<String> registerUser(@RequestBody String dadosUsuario) {
        // Define a URL do outro microserviço
        String urlOutroServico = "http://localhost:8081/api/contas";
        
        // Envia os dados recebidos (POST) e aguarda a resposta
        String resposta = rabbitTemplate.convertSendAndReceive(urlOutroServico, dadosUsuario).toString();
        
        return ResponseEntity.ok(resposta);
    }
}
