package br.com.fusion.banck.adapter.out.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import br.com.fusion.banck.domain.entity.FusionApiEntity;
import br.com.fusion.banck.application.service.FusionServices;

@Component
@RequiredArgsConstructor
public class FusionBankApiRabbitProducer {
    
    private final FusionServices fusionServices;

    // Responsavel por enviar os dados do controler para o outro microserviço.
    public void sendQueue(String exchanger, String routingKey, Object dadosUsuario) {

        // Chama o metodo enviar da classe services para enviar a mensagem para a fila do RabbitMQ
        fusionServices.sendMsgm(exchanger, routingKey, dadosUsuario);

    }

}