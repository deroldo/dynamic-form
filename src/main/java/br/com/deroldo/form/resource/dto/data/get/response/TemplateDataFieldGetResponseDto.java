package br.com.deroldo.form.resource.dto.data.get.response;

import br.com.deroldo.form.entity.TemplateField;

public class TemplateDataFieldGetResponseDto {

    private String label;

    public TemplateDataFieldGetResponseDto() {
    }

    public TemplateDataFieldGetResponseDto(final TemplateField templateField) {
        this.label = templateField.getLabel();
    }

    public String getLabel() {
        return label;
    }
}
