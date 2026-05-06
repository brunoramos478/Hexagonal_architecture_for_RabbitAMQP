package br.com.fusion.banck.shared.enums;


import lombok.Getter;

@Getter
public enum StatusMessage {
    PROCESSING("PROCESSING"),
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    DANIED("DANIED");

    private final String description;

    StatusMessage(String description) {
        this.description = description;
    }
}