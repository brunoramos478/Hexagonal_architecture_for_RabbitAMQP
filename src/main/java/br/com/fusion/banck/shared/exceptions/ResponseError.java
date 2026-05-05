package br.com.fusion.banck.shared.exceptions;

import br.com.fusion.banck.shared.enums.StatusMessage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseError {
    @Builder.Default
    private String statusMessage = StatusMessage.FAILED.getDescription();
    private int statusCode;
    private String message;
    private long timestamp;

}
