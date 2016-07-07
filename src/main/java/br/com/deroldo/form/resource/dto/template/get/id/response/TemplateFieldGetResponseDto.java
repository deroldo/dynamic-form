package br.com.deroldo.form.resource.dto.template.get.id.response;

import br.com.deroldo.form.entity.TemplateField;

import java.util.List;
import java.util.stream.Collectors;

public class TemplateFieldGetResponseDto {

    private String label;

    private String type;

    private boolean required;

    private boolean readOnly;

    private String value;

    private Integer maxLength;

    private String placeholder;

    private List<RadioGetResponseDto> radios;

    public TemplateFieldGetResponseDto() {
    }

    public TemplateFieldGetResponseDto(final TemplateField templateField) {
        this.label = templateField.getLabel();
        this.type = templateField.getType().getTextType();
        this.required = templateField.isRequired();
        this.readOnly = templateField.isReadOnly();
        this.value = templateField.getValue();
        this.maxLength = templateField.getMaxLength();
        this.placeholder = templateField.getPlaceholder();
        this.radios = templateField.getRadios().stream().map(RadioGetResponseDto::new).collect(Collectors.toList());
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public String getValue() {
        return value;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public List<RadioGetResponseDto> getRadios() {
        return radios;
    }
}
