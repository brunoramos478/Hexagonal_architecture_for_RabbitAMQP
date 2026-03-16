package br.com.fusion.banck.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation. *;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.Duration;
import java.time.OffsetDateTime;

@Component
public class FusionTimeInterception implements HandlerInterceptor {

    @Autowired
    private FusionServices fusionServices;

    // Limite de atraso do relogio do cliente.
    private static final int TOLERANT_TIME = 25;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String timestampHeader = request.getHeader("X-Fusion-Timestamp");

        if (timestampHeader == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Relógio ausente");
            return false;
        }

        try {
            OffsetDateTime clientTime = OffsetDateTime.parse(timestampHeader);

            OffsetDateTime serverTime = fusionServices.serverCheckHours();

            long diffTime = Duration.between(serverTime, clientTime).abs().getSeconds();

            if (diffTime > TOLERANT_TIME) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Erro de sincronia temporal");
                return false;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de data invalido");
            return false;
        }

        return true;
    }
}