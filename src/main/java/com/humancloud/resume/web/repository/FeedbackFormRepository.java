package com.humancloud.resume.web.repository;

import com.humancloud.resume.web.entity.InterviewFeedbackForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackFormRepository extends JpaRepository<InterviewFeedbackForm, Integer> {

    Optional<InterviewFeedbackForm> findByCandidateName(String name);
}
