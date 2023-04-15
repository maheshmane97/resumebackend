package com.humancloud.resume.web.controller;

import com.humancloud.resume.web.entity.SoftSkills;
import com.humancloud.resume.web.entity.Technology;
import com.humancloud.resume.web.repository.SoftSkillsRepository;
import com.humancloud.resume.web.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/thor/skill")
public class SkillsController {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @GetMapping("/techstack")
    public Technology findByName(@RequestParam String techName) {
        return technologyRepository.findByTechName(techName).get();
    }

    @GetMapping("/softskills")
    public List<SoftSkills> getAll() {
        return softSkillsRepository.findAll();
    }
    @GetMapping("/findall")
    public List<Technology> findall(){
        return technologyRepository.findAll();
    }
    @GetMapping("/softskill")
    public List<SoftSkills> findallSoftSkill(){
        return softSkillsRepository.findAll();
    }
}
