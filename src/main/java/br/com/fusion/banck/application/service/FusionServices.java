package br.com.fusion.banck.application.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import br.com.fusion.banck.shared.exceptions.NotSendQueue;
import br.com.fusion.banck.shared.exceptions.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fusion.banck.domain.entity.FusionApiEntity;

@Service
@RequiredArgsConstructor
public class FusionServices {

    @Value("${fila.DB}")
    private String fila_DB;

    private final TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    private static final Logger logger = LoggerFactory.getLogger(FusionServices.class);
    // Gera a criptografia do objeto Json
    public String encryptJson(Object delivery) {
        try {
            String completeJson = objectMapper.writeValueAsString(delivery);

            return textEncryptor.encrypt(completeJson);
        }
        catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
    @Async("taskExecutor")
    public void sendMsgm(String exchanger, String routingKey, Object message) {

        // Criptografa a mensagem do payload usando um metodo da classe services.
        String encryptedMessage = encryptJson(message);

        // Envia a mensagem criptografada para a fila do RabbitMQ.
        try {
            rabbitTemplate.convertAndSend(exchanger, routingKey, encryptedMessage);
        }
        catch (Exception e) {
            throw new NotSendQueue();
        }

    }


    // Metodo util pra ver se a aplicação e fila está online. Vou está melhorando mais.
    // Em contraproposta pode aumentar o tempo de resposta
    public String isOnline(String suafila_Application) {
        var fila_Application = suafila_Application;

        var applicationIsOn = rabbitAdmin.getQueueProperties(fila_Application);

        Integer count = (Integer) applicationIsOn.get(rabbitAdmin.QUEUE_CONSUMER_COUNT);

        if (count == 0 || count == null) {
            return "Aplicação offline, conexão recusada";
        }
        else {
            return"Aplicação online, conexão estabelecida com sucesso! Processando...";
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

