package com.humancloud.resume.web.helper;

import com.humancloud.resume.web.entity.Role;
import com.humancloud.resume.web.entity.SoftSkills;
import com.humancloud.resume.web.entity.TechnicalSkills;
import com.humancloud.resume.web.entity.Technology;
import com.humancloud.resume.web.repository.RoleRepository;
import com.humancloud.resume.web.repository.SoftSkillsRepository;
import com.humancloud.resume.web.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataObject {

    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public void techSkillData() {
        Technology tech1 = new Technology(1, "java");
        List<TechnicalSkills> list = Arrays.asList(new TechnicalSkills(1, "Java8"), new TechnicalSkills(2, "Jdbc"),
                new TechnicalSkills(3, "Hibernate"), new TechnicalSkills(4, "Multithreading"), new TechnicalSkills(5, "Spring boot"),
                new TechnicalSkills(6, "Microservice"));
        tech1.setTechSkills(list);


        Technology tech2 = new Technology(2, "Python");
        List<TechnicalSkills> list2 = Arrays.asList(new TechnicalSkills(7, "Django"), new TechnicalSkills(8, "Cloud Computing"),
                new TechnicalSkills(9, "APIs"), new TechnicalSkills(10, "AWS"), new TechnicalSkills(11, "Devops"),
                new TechnicalSkills(12, "Microservice"));
        tech2.setTechSkills(list2);

        Technology tech3 = new Technology(3, "Devops");
        List<TechnicalSkills> list3 = Arrays.asList(
                new TechnicalSkills(13, "AWS"), new TechnicalSkills(14, "AIOps"), new TechnicalSkills(15, "ModelOps"),
                new TechnicalSkills(16, "GitOps"));
        tech3.setTechSkills(list3);

        Technology tech4 = new Technology(4, "JavaScriprt");
        List<TechnicalSkills> list4 = Arrays.asList(
                new TechnicalSkills(17, "ReactJS"), new TechnicalSkills(18, "NodeJS"), new TechnicalSkills(19, "BootStrap"),
                new TechnicalSkills(20, "JQuery"),new TechnicalSkills(21, "MUI"),new TechnicalSkills(22, "CHAKRAUI"), new TechnicalSkills(23, "HTML5"));
        tech4.setTechSkills(list4);

        List<Technology> techList=Arrays.asList(tech1,tech2, tech3, tech4);
        technologyRepository.saveAll(techList);

    }

    @Bean
    public void saveSoftSkills(){
        List<SoftSkills> list=Arrays.asList(new SoftSkills(1,"Practical Knowledge"),new SoftSkills(2,"Logical Reasoning"),
                new SoftSkills(3,"Relevant Work Experience"),new SoftSkills(4,"Communication Skill"),
                new SoftSkills(5,"Behavior and Attitude"));
        softSkillsRepository.saveAll(list);
    }

    @Bean
    public void saveRole(){
        List<Role> roleList = Arrays.asList(new Role(1l, "ADMIN"),new Role(2l,"USER"));
        roleRepository.saveAll(roleList);
    }

}
