package app.model;

import app.util.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String fullName;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.NATURAL ,pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "group_num")
    private Integer group;

    @Column
    private Integer course;

    @ElementCollection(targetClass = Subject.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "stud_subj", joinColumns = @JoinColumn(name = "stud_id"))
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.NATURAL)
    private Set<Subject> subjects;

    public Student(Long id, String fullName, LocalDate birthday, Integer group, Integer course, Set<Subject> subjects) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.group = group;
        this.course = course;
        this.subjects = subjects;
    }

    public Student(String fullName, LocalDate birthday, Integer group, Integer course, Set<Subject> subjects) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.group = group;
        this.course = course;
        this.subjects = subjects;
    }

    public Student() {
    }
}
