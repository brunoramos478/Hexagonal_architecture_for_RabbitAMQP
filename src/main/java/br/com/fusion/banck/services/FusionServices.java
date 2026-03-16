package br.com.fusion.banck.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.fusion.banck.entity.FusionApiEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class FusionServices {

    @Value("${fila.DB}")
    private String fila_DB;

    private final TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    private static final Logger logger = LoggerFactory.getLogger(FusionServices.class);
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
    @Cacheable(value = "create-account")
    public String sendMsgm(FusionApiEntity message) {

        // Criptografa a mensagem do payload usando um metodo da classe services.
        String encryptedMessage = encryptJson(message);

        // Envia a mensagem criptografada para a fila do RabbitMQ.
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, encryptedMessage);

         // Pode aumentar o tempo de resposta
         // System.out.println(isOnline(fila_DB));
          System.out.println("Hora do servidor: " + serverCheckHours()+ " Fuso horário UTC global.");

        return "Mensagem enviada com sucesso!";
    }


    // Metodo util pra ver se a aplicação e fila está online. Vou está melhorando mais.
    // Em contraproposta pode aumentar o tempo de resposta
    public String isOnline(String suafila_Application) {
        var fila_Application = suafila_Application;

        var applicationIsOn = rabbitAdmin.getQueueProperties(fila_Application);

        Integer count = (Integer) applicationIsOn.get(rabbitAdmin.QUEUE_CONSUMER_COUNT);

        if (count == 0 || count == null) {
            return "Aplição offline, conexão recusada";
        }
        else {
            return"Aplição online, conexão estabelecida com sucesso! Processando...";
        }

    }
    // Pode ser usado pra exibir informações no console. Ideal pra mensagens simples.
    public void loggerInfo(String message) {
        logger.info(message);
    }


    // Define a hora do servidor baseado no fuso horário UTC global.
    public OffsetDateTime serverCheckHours() {
        OffsetDateTime serverHours = OffsetDateTime.now(ZoneOffset.UTC);
        return serverHours;
    }

}
