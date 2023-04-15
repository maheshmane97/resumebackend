package com.humancloud.resume.web.repository;

import com.humancloud.resume.web.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnologyRepository extends JpaRepository<Technology,Integer> {

    Optional<Technology> findByTechName(String techName);
}
