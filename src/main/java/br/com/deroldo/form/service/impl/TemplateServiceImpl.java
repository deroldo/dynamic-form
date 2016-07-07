package br.com.deroldo.form.service.impl;

import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.exception.NotFoundException;
import br.com.deroldo.form.repository.TemplateRepository;
import br.com.deroldo.form.resource.dto.data.get.response.TemplateDataGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.TemplateGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.list.response.TemplateGetListResponseDto;
import br.com.deroldo.form.resource.dto.template.post.request.TemplatePostRequestDto;
import br.com.deroldo.form.resource.dto.template.post.response.TemplatePostResponseDto;
import br.com.deroldo.form.resource.dto.template.put.request.TemplatePutRequestDto;
import br.com.deroldo.form.resource.dto.template.put.response.TemplatePutResponseDto;
import br.com.deroldo.form.service.TemplateService;
import br.com.deroldo.form.validator.TemplateDataValidator;
import br.com.deroldo.form.validator.TemplateValidator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateValidator validator;

    @Autowired
    private TemplateDataValidator templateDataValidator;

    @Autowired
    private TemplateRepository repository;

    @Override
    public TemplatePostResponseDto createTemplate(final TemplatePostRequestDto dto) {
        final Template template = dto.createEntity();
        final Template savedTemplate = saveTemplate(template);
        return new TemplatePostResponseDto(savedTemplate);
    }

    @Override
    public TemplatePutResponseDto updateTemplate(final String id, final TemplatePutRequestDto dto) {
        final Template template = dto.createEntity(id);
        final Template savedTemplate = saveTemplate(template);
        return new TemplatePutResponseDto(savedTemplate);
    }

    @Override
    public List<TemplateGetListResponseDto> listTemplate() {
        final List<Template> templates = this.repository.findAll();
        return templates.stream().map(TemplateGetListResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public TemplateGetResponseDto getTemplate(final String id) {
        final Template template = findTemplate(id);
        return new TemplateGetResponseDto(template);
    }

    @Override
    public void deleteTemplate(final String id) {
        this.repository.delete(new ObjectId(id));
    }

    @Override
    public void createData(final String id, final Map<String, String> params) {
        final Template template = findTemplate(id);
        this.templateDataValidator.validate(template, params);
        template.addData(params);
        saveTemplate(template);
    }

    @Override
    public TemplateDataGetResponseDto getData(final String id) {
        final Template template = findTemplate(id);
        return new TemplateDataGetResponseDto(template);
    }

    private Template saveTemplate(final Template template) {
        this.validator.validate(template);
        return this.repository.save(template);
    }

    private Template findTemplate(final String id) {
        final Template template = this.repository.findOne(new ObjectId(id));

        if (template == null){
            throw new NotFoundException("Template não encontrado");
        }

        return template;
    }

}
