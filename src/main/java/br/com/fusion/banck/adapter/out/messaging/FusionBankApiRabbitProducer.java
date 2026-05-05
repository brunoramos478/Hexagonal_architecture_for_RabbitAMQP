package br.com.fusion.banck.adapter.out.messaging;

import org.springframework.stereotype.Component;

import br.com.fusion.banck.domain.entity.FusionApiEntity;
import br.com.fusion.banck.application.service.FusionServices;

@Component
public class FusionBankApiRabbitProducer {

    
    private final FusionServices fusionServices;

    public FusionBankApiRabbitProducer(FusionServices fusionServices) {
        this.fusionServices = fusionServices;
    }

    // Responsavel por enviar os dados do controler para o outro microserviço.
    public Object sendQueue(String exchanger, String routingKey, Object dadosUsuario) {

        // Chama o metodo enviar da classe services para enviar a mensagem para a fila do RabbitMQ
        Object response = fusionServices.sendMsgm(exchanger, routingKey, dadosUsuario);

        return response;
    }

}