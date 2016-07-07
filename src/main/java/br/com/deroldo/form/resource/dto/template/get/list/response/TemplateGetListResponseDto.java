package br.com.deroldo.form.resource.dto.template.get.list.response;

import br.com.deroldo.form.entity.Template;

import java.util.List;
import java.util.stream.Collectors;

public class TemplateGetListResponseDto {

    private String _id;

    private String title;

    private List<TemplateFieldGetListResponseDto> fields;

    private String dataCount;

    public TemplateGetListResponseDto() {
    }

    public TemplateGetListResponseDto(final Template template) {
        this._id = template.getId().toString();
        this.title = template.getTitle();
        this.fields = template.getFields().stream().map(TemplateFieldGetListResponseDto::new).collect(Collectors.toList());
        this.dataCount = Integer.valueOf(template.getData().size()).toString();
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public List<TemplateFieldGetListResponseDto> getFields() {
        return fields;
    }

    public String getDataCount() {
        return dataCount;
    }
}
