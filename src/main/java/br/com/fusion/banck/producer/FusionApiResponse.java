package br.com.fusion.banck.producer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FusionApiResponse {

    @RabbitListener(queues = "cadastro-cliente-response-queue")
    public void receiveMsgm(String response) {

    }
}
