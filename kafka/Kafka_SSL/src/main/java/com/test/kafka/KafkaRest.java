package com.test.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaRest 
{

	@Autowired
	@Qualifier("empKafkaTemplate")
	private KafkaTemplate<String, Employee> empKafkaTemplate;
	
	@Autowired
	@Qualifier("studKafkaTemplate")
	private KafkaTemplate<String, Student> studKafkaTemplate;
	
	@PostMapping("/emp")
	public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {
		empKafkaTemplate.send("test",employee);
		return ResponseEntity.ok().body(employee);
	}
	
	@PostMapping("/stud")
	public ResponseEntity<Student> postStudent(@RequestBody Student student) {
		studKafkaTemplate.send("test",student);
		return ResponseEntity.ok().body(student);
	}
}
