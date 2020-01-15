package com.test.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class KafkaConsumerListenerService 
{
 /*
	@KafkaListener(topics = "test",groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
	public void listen(String employee) throws Exception, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Employee emp = mapper.readValue(employee, Employee.class);
	    System.out.println("*****\n*****\n*****\n*****\nReceived Messasge in group foo: " + emp);
	}
	*/
	@KafkaListener(topics = "test",groupId = "group1", containerFactory = "empKafkaListenerContainerFactory")
	public void listen(Employee employee) throws Exception, JsonProcessingException {
		System.out.println("*****\n*****\n*****\n*****\nEmployee: " + employee);
	}
	
	
}
