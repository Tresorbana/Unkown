package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GradingService {

    private final ExamAttemptRepository attemptRepo;
    private final AnswerRepository answerRepo;

    public GradingService(ExamAttemptRepository attemptRepo, AnswerRepository answerRepo) {
        this.attemptRepo = attemptRepo;
        this.answerRepo  = answerRepo;
    }

    public ExamResult autoGrade(Long attemptId) {
        ExamAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        List<Answer> answers = answerRepo.findByAttempt(attempt);
        int totalScore = 0;

        for (Answer answer : answers) {
            Question q = answer.getQuestion();
            if (q.getType() == Question.QuestionType.MCQ_SINGLE ||
                q.getType() == Question.QuestionType.TRUE_FALSE) {
                if (isCorrect(answer, q)) {
                    totalScore += q.getMarks();
                }
            }
        }

        ExamResult result = new ExamResult();
        result.setAttempt(attempt);
        result.setScore(totalScore);
        result.setPassed(totalScore >= attempt.getExam().getPassMark());
        attempt.setResult(result);
        attemptRepo.save(attempt);
        return result;
    }

    private boolean isCorrect(Answer answer, Question question) {
        return question.getOptions().stream()
                .filter(QuestionOption::isCorrect)
                .anyMatch(opt -> opt.getId().equals(answer.getSelectedOptionId()));
    }
}
