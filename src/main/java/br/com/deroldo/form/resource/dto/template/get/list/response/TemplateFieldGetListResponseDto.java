package br.com.deroldo.form.resource.dto.template.get.list.response;

import br.com.deroldo.form.entity.TemplateField;

public class TemplateFieldGetListResponseDto {

    private String label;

    public TemplateFieldGetListResponseDto() {
    }

    public TemplateFieldGetListResponseDto(final TemplateField templateField) {
        this.label = templateField.getLabel();
    }

    public String getLabel() {
        return label;
    }
}
