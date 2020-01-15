package com.test.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.test.kafka.Student;
import com.test.kafka.Employee;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // SSL in 9093
        props.put("bootstrap.servers", "http://localhost:9093");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", JsonSerializer.class);
        props.put("spring.json.add.type.headers", false);
        props.put("security.protocol", "SSL");
        props.put("ssl.truststore.location", "c:\\client_sll\\kafka.client.truststore.jks");
        props.put("ssl.truststore.password", "clientpass"); // client password
        return props;
    }

    @Bean
    public ProducerFactory<String, Employee> empProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    @Qualifier("empKafkaTemplate")
    public KafkaTemplate<String, Employee> empKafkaTemplate() {
        return new KafkaTemplate<>(empProducerFactory());
    }

    @Bean
    public ProducerFactory<String,Student> studProducerFactory() {
    	return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    
    @Bean
    @Qualifier("studKafkaTemplate")
    public KafkaTemplate<String,Student> studKafkaTemplate() {
    	return new KafkaTemplate<>(studProducerFactory());
    }

}
