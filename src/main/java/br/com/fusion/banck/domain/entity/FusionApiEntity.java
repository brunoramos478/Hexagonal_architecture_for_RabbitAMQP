package br.com.fusion.banck.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FusionApiEntity implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String cpf;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String email;
    private String region;
    private String city;
    private String state;
    private String cep;
    private String password;
    private LocalDateTime createdDate;
}
