package br.com.deroldo.form.resource.dto.template.post.response;

import br.com.deroldo.form.entity.Radio;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;

import java.util.List;
import java.util.stream.Collectors;

public class TemplatePostResponseDto {

    private String _id;

    private String title;

    private List<TemplateFieldPostResponseDto> fields;

    public TemplatePostResponseDto() {
    }

    public TemplatePostResponseDto(final Template template) {
        this._id = template.getId().toString();
        this.title = template.getTitle();
        this.fields = template.getFields().stream()
                .map(TemplateFieldPostResponseDto::new)
                .collect(Collectors.toList());;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldPostResponseDto> getFields() {
        return fields;
    }

}
