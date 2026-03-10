package br.com.fusion.banck.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class FusionBankApiRabbitConsumer {

    private final RestTemplate restTemplate = new RestTemplate();
    
	@RabbitListener(queues = "${fusion.queue.name}")
	public void consume(String message) {
		System.out.println("Message received: " + message);

		// Envia os dados para o outro microserviço (exemplo: localhost na porta 8081)
		String urlOutroServico = "http://localhost:8081/api/endpoint-destino";
		restTemplate.postForObject(urlOutroServico, message, String.class);
	}

}