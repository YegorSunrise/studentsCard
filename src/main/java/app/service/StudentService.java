package app.service;

import app.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudent(Long id);

    Student saveStudent(Student student);

    void deleteStudent(Long id);


}
