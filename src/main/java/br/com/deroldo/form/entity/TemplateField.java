package br.com.deroldo.form.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TemplateField {

    @NotBlank
    private String label;

    @NotNull
    private FieldType type;

    private boolean required;

    private boolean readOnly;

    private Integer maxLength;

    @Valid
    private List<Radio> radios;

    private String placeholder;

    private String value;

    public TemplateField(final String label, final FieldType type, final boolean required,
                         final boolean readOnly,final Integer maxLength, final List<Radio> radios,
                         final String placeholder, final String value) {
        this.label = label;
        this.type = type;
        this.required = required;
        this.readOnly = readOnly;
        this.maxLength = maxLength;
        this.radios = radios;
        this.placeholder = placeholder;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public FieldType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public List<Radio> getRadios() {
        return radios;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getValue() {
        return value;
    }
}
