package com.template.controller;

import com.template.entity.VideoLesson;
import com.template.service.LessonService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/courses/{courseId}/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public Flux<VideoLesson> getLessons(@PathVariable Long courseId) {
        return lessonService.findByCourse(courseId);
    }

    @GetMapping("/{lessonId}")
    public Mono<VideoLesson> getLesson(@PathVariable Long courseId,
                                       @PathVariable Long lessonId) {
        return lessonService.findById(lessonId);
    }

    @PostMapping("/{lessonId}/complete")
    public Mono<Void> markComplete(@PathVariable Long lessonId,
                                   @RequestParam Long studentId) {
        return lessonService.markComplete(studentId, lessonId);
    }
}
