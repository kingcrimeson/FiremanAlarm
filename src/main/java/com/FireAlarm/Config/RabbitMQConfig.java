package com.FireAlarm.Config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue alarmQueue() {
        return new Queue("alarm.queue", true);
    }

    @Bean
    public TopicExchange alarmTopicExchange() {
        return new TopicExchange("alarm.exchange");
    }

    @Bean
    public Binding bindingAlarmQueue(Queue alarmQueue, TopicExchange alarmTopicExchange) {
        return BindingBuilder.bind(alarmQueue).to(alarmTopicExchange).with("alarm.queue");
    }
}
