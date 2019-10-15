package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
  @Query("select s from Student s join fetch s.test")	
  public List<Student> getStudentAndTest();
}
