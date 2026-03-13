package br.com.fusion.banck.producer;

import br.com.fusion.banck.entity.FusionApiEntity;
import br.com.fusion.banck.services.FusionServices;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class FusionBankApiRabbitProducer {

    private final RabbitTemplate rabbitTemplate;
    private final FusionServices fusionServices;


    public FusionBankApiRabbitProducer(RabbitTemplate rabbitTemplate, FusionServices fusionServices) {
        this.rabbitTemplate = rabbitTemplate;
        this.fusionServices = fusionServices;

    }

    // Responsavel por enviar os dados do controler para o outro microserviço.
    public Object sendQueue(FusionApiEntity message) {

        // Chama o metodo enviar da classe services para enviar a mensagem para a fila do RabbitMQ
        Object response = fusionServices.sendMsgm(message);

        return response;
    }

}