package br.com.fusion.banck.shared.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"name", "lastName", "region", "city", "state"})
@Getter
@Setter
public class DtoResponse {

    private String name;
    private String lastName;
    private String region;
    private String city;
    private String state;

}