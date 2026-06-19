package com.template.service;

import com.template.entity.*;
import com.template.repository.EnrolmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrolmentService {

    private final EnrolmentRepository enrolmentRepo;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrolmentService(EnrolmentRepository enrolmentRepo,
                            StudentService studentService,
                            CourseService courseService) {
        this.enrolmentRepo  = enrolmentRepo;
        this.studentService = studentService;
        this.courseService  = courseService;
    }

    public Enrolment enrol(Long studentId, Long courseId, String term, int year) {
        Student student = studentService.findById(studentId);
        Course  course  = courseService.findById(courseId);

        EnrolmentId id = new EnrolmentId(student, course, term, year);
        if (enrolmentRepo.existsById(id)) {
            throw new IllegalStateException("Student already enrolled in this course for " + term + " " + year);
        }

        Enrolment enrolment = new Enrolment();
        enrolment.setId(id);
        return enrolmentRepo.save(enrolment);
    }

    @Transactional(readOnly = true)
    public List<Enrolment> findByStudent(Long studentId) {
        return enrolmentRepo.findByIdStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Enrolment> findByCourse(Long courseId) {
        return enrolmentRepo.findByIdCourseId(courseId);
    }

    public void withdraw(Long studentId, Long courseId, String term, int year) {
        Student student = studentService.findById(studentId);
        Course  course  = courseService.findById(courseId);
        EnrolmentId id  = new EnrolmentId(student, course, term, year);
        enrolmentRepo.deleteById(id);
    }
}
