package br.com.deroldo.form.resource.dto.template.put.response;

import br.com.deroldo.form.entity.Template;

import java.util.List;
import java.util.stream.Collectors;

public class TemplatePutResponseDto {

    private String _id;

    private String title;

    private List<TemplateFieldPutResponseDto> fields;

    public TemplatePutResponseDto() {
    }

    public TemplatePutResponseDto(final Template template) {
        this._id = template.getId().toString();
        this.title = template.getTitle();
        this.fields = template.getFields().stream()
                .map(TemplateFieldPutResponseDto::new)
                .collect(Collectors.toList());
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldPutResponseDto> getFields() {
        return fields;
    }
}
