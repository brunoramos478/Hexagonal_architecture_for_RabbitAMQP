package br.com.fusion.banck.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;


@Configuration
public class FusionCryptoPayload {

    @Value("${encrypt.key}")
    private String passwordEncrypt;

    // Esse bean é responsável por um metedo em especifico no services no injetor ObjectMapper.
    // Além de ser responsável pela compreensão de datas no Json.
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text(passwordEncrypt, "6ae6a23f8bb1a4e8565da4b0");
    }

}
