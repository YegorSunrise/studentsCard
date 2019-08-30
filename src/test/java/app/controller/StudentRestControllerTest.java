package app.controller;

import app.config.SecurityConfig;
import app.config.TestConfig;
import app.config.WebConfig;
import app.model.Student;
import app.model.Subject;
import app.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        WebConfig.class, TestConfig.class, SecurityConfig.class
})
@WebAppConfiguration
public class StudentRestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @Autowired
    StudentService studentService;

    private LocalDate date = LocalDate.now();
    private Set<Subject> subjects = new HashSet<Subject>() {{
        add(Subject.ECONOMY);
        add(Subject.HISTORY);
        add(Subject.PROGRAMMING);
    }};
    private Student student1 = new Student(1L, "Semen", date, 344, 3, subjects);
    private Student student2 = new Student(2L, "Ivan", date, 244, 2, subjects);
    private Student student3 = new Student("Petr", date, 144, 1, subjects);
    private List<Student> students = new ArrayList<Student>() {{
        add(student1);
        add(student2);
    }};

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        when(studentService.getAllStudents()).thenReturn(students);
    }

    @Test
    public void shouldAuthError() throws Exception {
        mvc.perform(
                get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void listShouldOk() throws Exception {
        mvc.perform(
                get("/api/v1/students")
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Semen"))
                .andExpect(jsonPath("$[0].birthday").value(date.toString()))
                .andExpect(jsonPath("$[0].group").value(344))
                .andExpect(jsonPath("$[0].course").value(3))
                .andExpect(jsonPath("$[0].subjects").value(subjects.stream().map(Enum::name).collect(Collectors.toList())))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].fullName").value("Ivan"))
                .andExpect(jsonPath("$[1].birthday").value(date.toString()))
                .andExpect(jsonPath("$[1].group").value(244))
                .andExpect(jsonPath("$[1].course").value(2))
                .andExpect(jsonPath("$[1].subjects").value(subjects.stream().map(Enum::name).collect(Collectors.toList())));
    }

    @Test
    public void shouldNotFound() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());
        mvc.perform(
                get("/api/v1/students")
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetStudentById() throws Exception {
        when(studentService.getStudent(1L)).thenReturn(student1);
        mvc.perform(
                get("/api/v1/students/1")
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Semen"))
                .andExpect(jsonPath("$.birthday").value(date.toString()))
                .andExpect(jsonPath("$.group").value(344))
                .andExpect(jsonPath("$.course").value(3))
                .andExpect(jsonPath("$.subjects").value(subjects.stream().map(Enum::name).collect(Collectors.toList())));
    }

    @Test
    public void badId() throws Exception {
        mvc.perform(
                get("/api/v1/students/" + 3)
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBadGetRequest() throws Exception {
        mvc.perform(
                get("/api/v1/students/word")
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNoContent() throws Exception {
        mvc.perform(
                delete("/api/v1/students/" + 1)
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBadDeleteRequest() throws Exception {
        mvc.perform(
                delete("/api/v1/students/слово")
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreate() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mvc.perform(
                post("/api/v1/students")
                        .content(objectMapper.writeValueAsString(student3))
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Petr"))
                .andExpect(jsonPath("$.birthday").value(date.toString()))
                .andExpect(jsonPath("$.group").value(144))
                .andExpect(jsonPath("$.course").value(1))
                .andExpect(jsonPath("$.subjects").value(subjects.stream().map(Enum::name).collect(Collectors.toList())));
    }

    @Test
    public void shouldBadRequestCreate() throws Exception {
        objectMapper = new ObjectMapper();
        mvc.perform(
                post("/api/v1/students")
                        .content(objectMapper.writeValueAsString(student3))
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdate() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        student2.setFullName("Kirill");
        when(studentService.saveStudent(student2)).thenReturn(student2);
        mvc.perform(
                put("/api/v1/students")
                        .content(objectMapper.writeValueAsString(student2))
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.fullName").value("Kirill"))
                .andExpect(jsonPath("$.birthday").value(date.toString()))
                .andExpect(jsonPath("$.group").value(244))
                .andExpect(jsonPath("$.course").value(2))
                .andExpect(jsonPath("$.subjects").value(subjects.stream().map(Enum::name).collect(Collectors.toList())));
    }

    @Test
    public void shouldBadRequestUpdate() throws Exception {
        objectMapper = new ObjectMapper();
        student2.setFullName("Kirill");
        when(studentService.saveStudent(student2)).thenReturn(student2);
        mvc.perform(
                put("/api/v1/students")
                        .content(objectMapper.writeValueAsString(student2))
                        .header("KEY", "pass")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
