package br.com.fusion.banck.adapter.out.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FusionApiResponse {

    @RabbitListener(queues = "${fila.DB.Response}")
    public void receiveMsgm(String response) {

        System.out.println("Resposta recebida: " + response);

    }
}
