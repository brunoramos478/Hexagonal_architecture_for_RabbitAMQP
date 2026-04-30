package br.com.fusion.banck.shared.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Executor threadVirtual() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    // Responsavel por gerenciar e converter o Json em objeto.
    @Bean
    public MessageConverter messageConverter(){
        return new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) {
                if (object instanceof String) {
                    messageProperties.setContentType("text/plain");
                    return new Message(((String) object).getBytes(), messageProperties);
                }
                messageProperties.setContentType("text/plain");
                return new Message(object.toString().getBytes(), messageProperties);
            }

            @Override
            public Object fromMessage(Message message) {
                return new String(message.getBody());
            }
        };
    }

}
