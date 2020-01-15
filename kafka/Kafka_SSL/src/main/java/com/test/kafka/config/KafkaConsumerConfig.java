package com.test.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.test.kafka.Employee;

@EnableKafka
@Configuration
public class KafkaConsumerConfig 
{
	    @Bean
	    public ConsumerFactory<String, String> consumerFactory() {
	    	 Map<String, Object> props = new HashMap<>();
		        // SSL in 9093
		        props.put("bootstrap.servers", "http://localhost:9093");
		        props.put("key.deserializer", StringDeserializer.class);
		        props.put("value.deserializer", StringDeserializer.class);
		        props.put("group.id", "group1");
		        props.put("security.protocol", "SSL");
		        props.put("ssl.truststore.location", "c:\\client_sll\\kafka.client.truststore.jks");
		        props.put("ssl.truststore.password", "clientpass"); // client password

	        return new DefaultKafkaConsumerFactory<>(props);
	    }

	    @Bean
	    @Qualifier("kafkaListenerContainerFactory")
	    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
	        factory.setConsumerFactory(consumerFactory());
	        return factory;
	    }

	    @Bean
	    public ConsumerFactory<String, Employee> empConsumerFactory() {
	    	 Map<String, Object> props = new HashMap<>();
		        // SSL in 9093
		        props.put("bootstrap.servers", "http://localhost:9093");
		        props.put("key.deserializer", StringDeserializer.class);
		        props.put("value.deserializer", JsonDeserializer.class);
		        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		        props.put("group.id", "group1");
		        props.put("security.protocol", "SSL");
		        props.put("ssl.truststore.location", "c:\\client_sll\\kafka.client.truststore.jks");
		        props.put("ssl.truststore.password", "clientpass"); // client password
	        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
	                new JsonDeserializer<>(Employee.class));
	    }

	    @Bean
	    @Qualifier("empKafkaListenerContainerFactory")
	    public ConcurrentKafkaListenerContainerFactory<String, Employee> empKafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(empConsumerFactory());
	        return factory;
	    }

}
