package app.service;

import app.model.Student;
import app.repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> getAllStudents() {
        log.info("get all students");
        return studentRepo.findAll();
    }

    @Override
    public Student getStudent(Long id) {
        log.info("get student {}", id);
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public Student saveStudent(Student student) {
        log.info("save student {}", student);
        return studentRepo.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("delete student {}", id);
        studentRepo.deleteById(id);
    }
}
