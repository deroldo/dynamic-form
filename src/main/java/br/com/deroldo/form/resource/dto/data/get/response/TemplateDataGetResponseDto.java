package br.com.deroldo.form.resource.dto.data.get.response;

import br.com.deroldo.form.entity.Template;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateDataGetResponseDto {

    private List<TemplateDataFieldGetResponseDto> fields;

    private List<Map<String, String>> data;

    public TemplateDataGetResponseDto() {
    }

    public TemplateDataGetResponseDto(final Template template) {
        this.fields = template.getFields().stream().map(TemplateDataFieldGetResponseDto::new).collect(Collectors.toList());
        this.data = template.getData();
    }

    public List<TemplateDataFieldGetResponseDto> getFields() {
        return fields;
    }

    public List<Map<String, String>> getData() {
        return data;
    }
}
