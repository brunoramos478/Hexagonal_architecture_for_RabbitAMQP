package br.com.fusion.banck.services;

import br.com.fusion.banck.entity.FusionApiEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class FusionServices {

    @Value("${fila.DB}")
    private String fila_DB;
    private String applicationIsOnline;

    private final TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    private static final String EXCHANGE_NAME = "fusion.exchange";
    private static final String ROUTING_KEY = "fusion.routing.key";

    // Construtor pra fazer a injeção de dependencia.
    public FusionServices(TextEncryptor textEncryptor, ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
        this.textEncryptor = textEncryptor;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }

    // Gera a criptografia do objeto Json convertendo em String.
    public String encryptJson(FusionApiEntity delivery) {
        try {
            String completeJson = objectMapper.writeValueAsString(delivery);

            return textEncryptor.encrypt(completeJson);
        }
        catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
    // Metodo responsavel por enviar a mensagem para a fila do RabbitMQ.
    public String sendMsgm(FusionApiEntity message) {

        // Criptografa a mensagem do payload usando um metodo da classe services.
        String encryptedMessage = encryptJson(message);

        // Envia a mensagem criptografada para a fila do RabbitMQ.
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, encryptedMessage);

         // System.out.println(isOnline(fila_DB));

        return "Mensagem enviada com sucesso!";
    }

    public String isOnline(String fila_Application) {
        this.applicationIsOnline = fila_Application;

        var applicationIsOn = rabbitAdmin.getQueueProperties(fila_Application);

        Integer count = (Integer) applicationIsOn.get(rabbitAdmin.QUEUE_CONSUMER_COUNT);

        if (count == 0 || count == null) {
            return "Aplição offline, conexão recusada";
        } else

        {
            return"Aplição online, conexão estabelecida com sucesso! Processando...";
        }

    }



}
