package br.com.fusion.banck.producer;

import br.com.fusion.banck.entity.FusionApiEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class FusionBankApiRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE_NAME = "fusion.exchange";
    public static final String ROUTING_KEY = "fusion.routing.key";

    public FusionBankApiRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }
    // Responsavel por enviar os dados do controler para o outro microserviço.
    public void sendQueue(FusionApiEntity message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);

    }

}