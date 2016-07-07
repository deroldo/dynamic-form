package br.com.deroldo.form.resource.dto.template.get.id.response;

import br.com.deroldo.form.entity.Template;

import java.util.List;
import java.util.stream.Collectors;

public class TemplateGetResponseDto {

    private String _id;

    private String title;

    private List<TemplateFieldGetResponseDto> fields;

    public TemplateGetResponseDto() {
    }

    public TemplateGetResponseDto(final Template template) {
        this._id = template.getId().toString();
        this.title = template.getTitle();
        this.fields = template.getFields().stream().map(TemplateFieldGetResponseDto::new).collect(Collectors.toList());
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldGetResponseDto> getFields() {
        return fields;
    }
}
