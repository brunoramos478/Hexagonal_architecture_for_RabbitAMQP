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
    public void sendQueue(FusionApiEntity message) {

        // Está criando gargalo no DB de 5ms pra 5s
        // Vou ver o melhor que posso  fazer em relaão a isso.
        // Uma das opções seria remover.
    Object responseByUserInDB = rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, ROUTING_KEY, message);
        System.out.println(responseByUserInDB);

        // Metodo do service que faz a criptografia do Json.
        String encryptedMessage = fusionServices.encryptJson(message);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, encryptedMessage);

    }

}