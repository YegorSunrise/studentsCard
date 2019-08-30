package app.controller;


import app.model.Student;
import app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StudentRestController {

    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> all = studentService.getAllStudents();

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        if (id == null || !id.matches("^[0-9]+$")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        long _id = Long.parseLong(id);
        Student student = studentService.getStudent(_id);

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        if (student == null || student.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable String id) {

        if (id == null || !id.matches("^[0-9]+$")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        long _id = Long.parseLong(id);
        studentService.deleteStudent(_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> update(@RequestBody Student student) {


        if (student == null || student.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.OK);

    }

}
