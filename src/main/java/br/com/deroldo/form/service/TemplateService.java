package br.com.deroldo.form.service;

import br.com.deroldo.form.resource.dto.data.get.response.TemplateDataGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.id.response.TemplateGetResponseDto;
import br.com.deroldo.form.resource.dto.template.get.list.response.TemplateGetListResponseDto;
import br.com.deroldo.form.resource.dto.template.post.request.TemplatePostRequestDto;
import br.com.deroldo.form.resource.dto.template.post.response.TemplatePostResponseDto;
import br.com.deroldo.form.resource.dto.template.put.request.TemplatePutRequestDto;
import br.com.deroldo.form.resource.dto.template.put.response.TemplatePutResponseDto;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public interface TemplateService {

    TemplatePostResponseDto createTemplate(TemplatePostRequestDto dto);

    TemplatePutResponseDto updateTemplate(String id, TemplatePutRequestDto dto);

    List<TemplateGetListResponseDto> listTemplate();

    TemplateGetResponseDto getTemplate(String id);

    void deleteTemplate(String id);

    void createData(String id, Map<String, String> params);

    TemplateDataGetResponseDto getData(String id);
}
