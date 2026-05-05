package br.com.fusion.banck.ports.out;

/**
 * Port de saída para mensageria RabbitMQ
 * Define o contrato para envio de mensagens para filas
 */
public interface FusionMessagingPort  {

    /**
     * Envia uma mensagem para a fila especificada
     * @param exchanger nome do exchange
     * @param routingKey chave de roteamento
     * @param message mensagem a ser enviada
     * @return resposta do envio da mensagem
     */
    Object sendMessage(String exchanger, String routingKey, Object message);
}

