package app.config;

import app.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public StudentService studentService(){
        return mock(StudentService.class);
    }
}
