package com.humancloud.resume.web.helper;

import com.humancloud.resume.web.entity.Project;
import com.humancloud.resume.web.entity.ResumeMasterEntity;
import com.humancloud.resume.web.entity.WorkExperience;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PDFGeneratorHelper {

     public void GeneratePDF(Optional<ResumeMasterEntity> resumeMasterEntity, HttpServletResponse response)
    {
        try
        {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font name_font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            name_font.setColor(new Color(111, 156, 250));
            name_font.setSize(23);

            Paragraph name = new Paragraph(resumeMasterEntity.get().getPersonalDetails().getEmpName(), name_font);
            name.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(name);

            com.lowagie.text.Image image = Image.getInstance("https://i.imgur.com/Qd09mpM.png");
            image.scalePercent (50.0f);
            image.setAbsolutePosition(460, 770);
            document.add(image);

            PdfPTable table_header = new PdfPTable(2);
            table_header.setWidthPercentage(100);
            table_header.setWidths(new int[] {6,6});
            table_header.setSpacingBefore(5);
            table_header.setSpacingAfter(10);
            PdfPCell cell_header = new PdfPCell();

            Font designation_font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            designation_font.setColor(CMYKColor.BLACK);
            designation_font.setSize(10);
            cell_header.setBorder(0);
            Font header_font = FontFactory.getFont(FontFactory.HELVETICA);
            header_font.setColor(CMYKColor.BLACK);
            header_font.setSize(10);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_header.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getDesignation(), designation_font));
            table_header.addCell(cell_header);

            cell_header.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell_header.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getEmail(), header_font));
            table_header.addCell(cell_header);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_header.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getAddress(), header_font));
            table_header.addCell(cell_header);
            cell_header.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell_header.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getMobileNo(), header_font));
            table_header.addCell(cell_header);
            document.add(table_header);

            //Professional Summary Table
            PdfPTable table_p_summary = new PdfPTable(1);
            table_p_summary.setWidthPercentage(100);
            table_p_summary.setWidths(new int[] {12});
            table_p_summary.setSpacingBefore(5);
            //table_p_summary.setSpacingAfter(5);

            PdfPCell cell_p_summary = new PdfPCell();
            cell_p_summary.setBackgroundColor(new Color(111, 156, 250));
            cell_p_summary.setPadding(5);

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setColor(CMYKColor.white);
            cell_p_summary.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_p_summary.setBorderColor(new Color(111, 156, 250));
            cell_p_summary.setPhrase(new Phrase("Professional Summary", font));
            table_p_summary.addCell(cell_p_summary);
            document.add(table_p_summary);

            PdfPTable table_p_summary_desc = new PdfPTable(1);
            table_p_summary_desc.setWidthPercentage(100);
            table_p_summary_desc.setWidths(new int[] {12});
            table_p_summary_desc.setSpacingBefore(5);
            table_p_summary_desc.setSpacingAfter(5);

            PdfPCell cell_p_summary_desc = new PdfPCell();
            Font font_p_summary_desc = FontFactory.getFont(FontFactory.HELVETICA);
            font_p_summary_desc.setSize(10);
            cell_p_summary_desc.setBorder(0);
            List <String> summaryDetails = resumeMasterEntity.get().getProfessionalSummary().getSummaryDetails();
            for (String summaryId: summaryDetails) {
                cell_p_summary_desc.setBorder(0);
                cell_p_summary_desc.setPhrase(new Phrase("• "+summaryId,font_p_summary_desc));
                table_p_summary_desc.addCell(cell_p_summary_desc);
            }
            document.add(table_p_summary_desc);

            //Skill Set Table
            PdfPTable table_skill_set = new PdfPTable(1);
            table_skill_set.setWidthPercentage(100);
            table_skill_set.setWidths(new int[] {12});
            table_skill_set.setSpacingBefore(5);

            PdfPCell cell_skill_set = new PdfPCell();
            cell_skill_set.setBackgroundColor(new Color(111, 156, 250));
            cell_skill_set.setPadding(5);
            cell_skill_set.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_skill_set.setBorderColor(new Color(111, 156, 250));
            cell_skill_set.setPhrase(new Phrase("Skill Set", font));
            table_skill_set.addCell(cell_skill_set);
            document.add(table_skill_set);

            PdfPTable table_skill_set_desc = new PdfPTable(2);
            table_skill_set_desc.setWidthPercentage(100);
            table_skill_set_desc.setWidths(new int[] {3, 9});
            table_skill_set_desc.setSpacingBefore(5);
            table_skill_set_desc.setSpacingAfter(10);

            PdfPCell cell_skill_set_desc = new PdfPCell();
            Font font_skill_set_desc_title = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            Font font_skill_set_desc = FontFactory.getFont(FontFactory.HELVETICA);
            font_skill_set_desc_title.setSize(10);
            font_skill_set_desc.setSize(10);
            cell_skill_set_desc.setBorder(0);

            if(resumeMasterEntity.get().getSkillSet().getTechnologies() != null && !resumeMasterEntity.get().getSkillSet().getTechnologies().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Technologies",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getTechnologies(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if(resumeMasterEntity.get().getSkillSet().getLanguages() != null && !resumeMasterEntity.get().getSkillSet().getLanguages().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Languages",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getLanguages(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if(resumeMasterEntity.get().getSkillSet().getTools() != null && !resumeMasterEntity.get().getSkillSet().getTools().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Tools",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getTools(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if(resumeMasterEntity.get().getSkillSet().getDatabaseUsed() != null && !resumeMasterEntity.get().getSkillSet().getDatabaseUsed().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Databases",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getDatabaseUsed(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if(resumeMasterEntity.get().getSkillSet().getOperatingSystems() != null && !resumeMasterEntity.get().getSkillSet().getOperatingSystems().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Operating Systems",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getOperatingSystems(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if( resumeMasterEntity.get().getSkillSet().getIdeUsed() != null && !resumeMasterEntity.get().getSkillSet().getIdeUsed().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("IDE's",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getIdeUsed(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }
            if(resumeMasterEntity.get().getSkillSet().getWebServer() != null && !resumeMasterEntity.get().getSkillSet().getWebServer().isEmpty()){
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_p_summary_desc.setPhrase(new Phrase("Application/Web Server's",font_skill_set_desc_title));
                table_skill_set_desc.addCell(cell_p_summary_desc);
                cell_skill_set_desc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell_p_summary_desc.setPhrase(new Phrase(resumeMasterEntity.get().getSkillSet().getWebServer(),font_skill_set_desc));
                table_skill_set_desc.addCell(cell_p_summary_desc);
            }

            document.add(table_skill_set_desc);

            //Work-Experience Table
            PdfPTable table_work_experience = new PdfPTable(1);

            table_work_experience.setWidthPercentage(100);
            table_work_experience.setWidths(new int[] {12});
            table_work_experience.setSpacingBefore(5);
            table_work_experience.setSpacingAfter(5);

            PdfPCell cell_work_experience = new PdfPCell();
            cell_work_experience.setBackgroundColor(new Color(111, 156, 250));
            cell_work_experience.setPadding(5);
            cell_work_experience.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_work_experience.setBorderColor(new Color(111, 156, 250));
            cell_work_experience.setPhrase(new Phrase("Work Experience", font));
            table_work_experience.addCell(cell_work_experience);
            document.add(table_work_experience);

            PdfPTable table_work_experience_desc = new PdfPTable(1);
            table_work_experience_desc.setWidthPercentage(100);
            table_work_experience_desc.setWidths(new int[] {12});
            table_work_experience_desc.setSpacingBefore(5);
            table_work_experience.setSpacingAfter(10);

            PdfPCell cell_work_experience_desc = new PdfPCell();
            Font font_work_experience_desc_title = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font_work_experience_desc_title.setSize(13);
            Font font_work_experience_desc_sub_title = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font_work_experience_desc_sub_title.setSize(10);
            Font font_work_experience_desc_project_title = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font_work_experience_desc_project_title.setSize(12);
            Font font_work_experience_desc = FontFactory.getFont((FontFactory.HELVETICA));
            font_work_experience_desc.setSize(10);
            cell_work_experience_desc.setBorder(0);
            for(WorkExperience workExperience : resumeMasterEntity.get().getWorkExperience())
            {
                System.out.println("Workexperience: "+workExperience);
                cell_work_experience_desc.setPhrase(new Phrase(workExperience.getJobRole()+" at "+workExperience.getCompany()+" ("+workExperience.getPeriodFrom()+" - "+workExperience.getPeriodTo()+")",font_work_experience_desc_title));
                table_work_experience_desc.addCell(cell_work_experience_desc);
                cell_work_experience_desc.setPhrase(new Phrase(" ",font_work_experience_desc_title));
                table_work_experience_desc.addCell(cell_work_experience_desc);
                for(Project project : workExperience.getProjects())
                {
                    System.out.println("project: "+project);
                    if(project.getProjectName() != null && !project.getProjectName().isEmpty()){
                        cell_work_experience_desc.setPhrase(new Phrase("Project:"+project.getProjectName(),font_work_experience_desc_project_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
//                        cell_work_experience_desc.setPhrase(new Phrase(" ",font_work_experience_desc_title));
//                        table_work_experience_desc.addCell(cell_work_experience_desc);
                    }
                    if(project.getDescription() != null && !project.getDescription().isEmpty()){
                        cell_work_experience_desc.setPhrase(new Phrase("Description:",font_work_experience_desc_sub_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                        cell_work_experience_desc.setPhrase(new Phrase(project.getDescription(),font_work_experience_desc));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                        cell_work_experience_desc.setPhrase(new Phrase(" ",font_work_experience_desc_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                    }
                    if(project.getResponsibilities() != null && !project.getResponsibilities().isEmpty()){
                        cell_work_experience_desc.setPhrase(new Phrase("Responsibilities:",font_work_experience_desc_sub_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                        for (String res :project.getResponsibilities())
                        {
                            cell_work_experience_desc.setPhrase(new Phrase("• "+res,font_work_experience_desc));
                            table_work_experience_desc.addCell(cell_work_experience_desc);
                        }
                        cell_work_experience_desc.setPhrase(new Phrase(" ",font_work_experience_desc_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                    }
                    if(project.getTechnologies() != null && !project.getTechnologies().isEmpty()){
                        cell_work_experience_desc.setPhrase(new Phrase("Technologies:",font_work_experience_desc_sub_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                        cell_work_experience_desc.setPhrase(new Phrase(project.getTechnologies(),font_work_experience_desc));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                        cell_work_experience_desc.setPhrase(new Phrase("  ",font_work_experience_desc_title));
                        table_work_experience_desc.addCell(cell_work_experience_desc);
                    }

                }
            }

            document.add(table_work_experience_desc);

            //Education Table
            PdfPTable table_education = new PdfPTable(1);

            table_education.setWidthPercentage(100);
            table_education.setWidths(new int[] {12});
//            table_education.setSpacingBefore(2);
//            table_education.setSpacingAfter(5);

            PdfPCell cell_education = new PdfPCell();
            cell_education.setBackgroundColor(new Color(111, 156, 250));
            cell_education.setPadding(5);

            cell_education.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_education.setBorderColor(new Color(111, 156, 250));
            cell_education.setPhrase(new Phrase("Education", font));
            table_education.addCell(cell_education);
            document.add(table_education);

            PdfPTable table_education_desc = new PdfPTable(1);
            table_education_desc.setWidthPercentage(100);
            table_education_desc.setWidths(new int[] {12});
//            table_education_desc.setSpacingBefore(1);
            table_education_desc.setSpacingAfter(5);

            PdfPCell cell_education_desc = new PdfPCell();
            Font font_education_desc = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font_education_desc.setSize(10);
            cell_education_desc.setBorder(0);
            cell_education_desc.setPhrase(new Phrase( resumeMasterEntity.get().getEducationDetails().getDegree()+" From "+resumeMasterEntity.get().getEducationDetails().getUniversity()+"("+resumeMasterEntity.get().getEducationDetails().getPassingYear()+").",font_education_desc));
            table_education_desc.addCell(cell_education_desc);
            document.add(table_education_desc);

            //Personal Details
            PdfPTable table_personal_details = new PdfPTable(1);

            table_personal_details.setWidthPercentage(100);
            table_personal_details.setWidths(new int[] {12});
            //table_personal_details.setSpacingBefore(2);
            table_personal_details.setSpacingAfter(3);

            PdfPCell cell_personal_details = new PdfPCell();
            cell_personal_details.setBackgroundColor(new Color(111, 156, 250));
            cell_personal_details.setPadding(5);

            cell_personal_details.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_personal_details.setBorderColor(new Color(111, 156, 250));
            cell_personal_details.setPhrase(new Phrase("Personal Details", font));
            table_personal_details.addCell(cell_personal_details);
            document.add(table_personal_details);

            PdfPTable table_personal_details_desc = new PdfPTable(2);
            table_personal_details_desc.setWidthPercentage(100);
            table_personal_details_desc.setWidths(new int[] {3,9});
            //table_personal_details_desc.setSpacingBefore(5);
            table_personal_details_desc.setSpacingAfter(5);

            PdfPCell cell_personal_details_desc = new PdfPCell();
            Font font_personal_details_dec = FontFactory.getFont(FontFactory.HELVETICA);
            font_personal_details_dec.setSize(10);
            Font font_personal_details_dec_content = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font_personal_details_dec_content.setSize(10);
            cell_personal_details_desc.setBorder(0);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase("Name",font_personal_details_dec));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getEmpName(),font_personal_details_dec_content));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase("Gender",font_personal_details_dec));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getGender(),font_personal_details_dec_content));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase("Marital Status",font_personal_details_dec));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            cell_personal_details_desc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_personal_details_desc.setPhrase(new Phrase(resumeMasterEntity.get().getPersonalDetails().getMaritalStatus(),font_personal_details_dec_content));
            table_personal_details_desc.addCell(cell_personal_details_desc);
            document.add(table_personal_details_desc);

            //Table Declaration
            PdfPTable table_declaration = new PdfPTable(1);
            table_declaration.setWidthPercentage(100);
            table_declaration.setWidths(new int[] {12});
            //table_declaration.setSpacingBefore(5);
            table_declaration.setSpacingAfter(5);

            PdfPCell cell_declaration = new PdfPCell();

            cell_declaration.setBackgroundColor(new Color(111, 156, 250));
            cell_declaration.setPadding(5);

            cell_declaration.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_declaration.setBorderColor(new Color(111, 156, 250));
            cell_declaration.setPhrase(new Phrase("Declaration", font));
            table_declaration.addCell(cell_declaration);
            document.add(table_declaration);

            PdfPTable table_declaration_desc = new PdfPTable(1);
            table_declaration_desc.setWidthPercentage(100);
            table_declaration_desc.setWidths(new int[] {12});
            //table_declaration_desc.setSpacingBefore(5);
            table_declaration_desc.setSpacingAfter(5);

            PdfPCell cell_declaration_desc = new PdfPCell();
            Font font_declaration_desc = FontFactory.getFont(FontFactory.HELVETICA);
            font_declaration_desc.setSize(10);
            cell_declaration_desc.setBorder(0);
            cell_declaration_desc.setPhrase(new Phrase("I hereby declare that the above-furnished details are true to the best of my knowledge.",font_declaration_desc));
            table_declaration_desc.addCell(cell_declaration_desc);
            cell_declaration_desc.setPhrase(new Phrase(" ",font_declaration_desc));
            table_declaration_desc.addCell(cell_declaration_desc);
            cell_declaration_desc.setPhrase(new Phrase(" ",font_declaration_desc));
            table_declaration_desc.addCell(cell_declaration_desc);
            cell_declaration_desc.setPhrase(new Phrase("Name:  "+resumeMasterEntity.get().getPersonalDetails().getEmpName(),font_declaration_desc));
            table_declaration_desc.addCell(cell_declaration_desc);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            cell_declaration_desc.setPhrase(new Phrase("Date:  "+simpleDateFormat.format(Date.from(Instant.now())),font_declaration_desc));
            table_declaration_desc.addCell(cell_declaration_desc);
            document.add(table_declaration_desc);

            document.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception in generating PDF file" +e);
        }
    }

}
