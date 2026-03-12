package br.com.fusion.banck.services;

import br.com.fusion.banck.entity.FusionApiEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class FusionServices {

    private final TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;


    // Construtor pra fazer a injeção de dependencia.
    public FusionServices(TextEncryptor textEncryptor, ObjectMapper objectMapper) {
        this.textEncryptor = textEncryptor;
        this.objectMapper = objectMapper;
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



}
