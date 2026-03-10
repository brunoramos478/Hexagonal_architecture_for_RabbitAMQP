package br.com.fusion.banck.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;

@Configuration
public class FusionApiRabbitMqConfig {

    @Value("${fusion.queue.name}")
    private String queueName;

    public static final String EXCHANGE_NAME = "fusion.exchange";
    public static final String ROUTING_KEY = "fusion.routing.key";

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // Responsavel por gerenciar e converter o Json em objeto.
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
}


}
