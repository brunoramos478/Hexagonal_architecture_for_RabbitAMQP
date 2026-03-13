package br.com.fusion.banck.producer;

import br.com.fusion.banck.entity.FusionApiEntity;
import br.com.fusion.banck.services.FusionServices;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class FusionBankApiRabbitProducer {

    private final RabbitTemplate rabbitTemplate;
    private final FusionServices fusionServices;

    public static final String EXCHANGE_NAME = "fusion.exchange";
    public static final String ROUTING_KEY = "fusion.routing.key";

    public FusionBankApiRabbitProducer(RabbitTemplate rabbitTemplate, FusionServices fusionServices) {
        this.rabbitTemplate = rabbitTemplate;
        this.fusionServices = fusionServices;

    }

    // Responsavel por enviar os dados do controler para o outro microserviço.
    public Object sendQueue(FusionApiEntity message) {

        // Criptografa o Json
        String encryptedMessage = fusionServices.encryptJson(message);

        System.out.println("A mensagem criptografada é: "+ encryptedMessage);

        // Envia a mensagem para a fila do RabbitMQ
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, encryptedMessage);

        Object response = rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, ROUTING_KEY, encryptedMessage);
        System.out.println("Resposta do banco:  " + response);
        return response;
    
        

    }

}