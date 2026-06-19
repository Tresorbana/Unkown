package com.template;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

/**
 * Reactive Programming template using Project Reactor.
 * Covers: Mono, Flux, operators, error handling, backpressure, schedulers.
 */
public class ReactiveTemplate {

    // ─── Mono ─────────────────────────────────────────────────────────────────

    /** Wrap a single value */
    public Mono<String> greet(String name) {
        return Mono.just("Hello, " + name);
    }

    /** Async DB call simulation */
    public Mono<Student> findStudentById(Long id) {
        return Mono.fromCallable(() -> fetchFromDb(id))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    // ─── Flux ─────────────────────────────────────────────────────────────────

    /** Emit a stream of students */
    public Flux<Student> allStudents() {
        return Flux.fromIterable(List.of(/* repo.findAll() */))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    /** Filter + map pipeline */
    public Flux<String> seniorStudentNames() {
        return allStudents()
                .filter(s -> s.getYear() >= 3)
                .map(s -> s.getFirstName() + " " + s.getLastName());
    }

    // ─── Error handling ───────────────────────────────────────────────────────

    public Mono<Student> findWithFallback(Long id) {
        return findStudentById(id)
                .onErrorReturn(new Student())          // fallback value
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Student> findWithRetry(Long id) {
        return findStudentById(id)
                .retry(3)
                .onErrorMap(ex -> new ServiceException("Failed after 3 retries", ex));
    }

    // ─── Combining streams ────────────────────────────────────────────────────

    public Flux<EnrolmentDto> studentEnrolments(Long studentId) {
        Mono<Student> studentMono  = findStudentById(studentId);
        Flux<Enrolment> enrolFlux = findEnrolmentsByStudent(studentId);

        return studentMono.flatMapMany(student ->
                enrolFlux.map(e -> new EnrolmentDto(student, e)));
    }

    // ─── Backpressure ─────────────────────────────────────────────────────────

    public Flux<Student> bufferedStudents() {
        return allStudents()
                .onBackpressureBuffer(100)    // buffer up to 100 items
                .delayElements(Duration.ofMillis(10));
    }

    // ─── Schedulers ───────────────────────────────────────────────────────────

    public Mono<String> cpuIntensiveTask(String input) {
        return Mono.fromCallable(() -> heavyComputation(input))
                   .subscribeOn(Schedulers.parallel());
    }

    // ─── stubs (replace with real implementations) ────────────────────────────
    private Student fetchFromDb(Long id) { return new Student(); }
    private Flux<Enrolment> findEnrolmentsByStudent(Long id) { return Flux.empty(); }
    private String heavyComputation(String in) { return in.toUpperCase(); }
}
