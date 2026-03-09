package br.com.fusion.banck.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

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
    public org.springframework.amqp.core.DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    
}
