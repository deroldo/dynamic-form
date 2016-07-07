package br.com.deroldo.form.resource.dto.template.post.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

public class TemplateFieldPostRequestDto {

    @NotBlank
    private String label;

    @NotBlank
    private String type;

    private boolean required;

    private boolean readOnly;

    private String value;

    private Integer maxLength;

    private String placeholder;

    @Valid
    private List<RadioPostRequestDto> radios;

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

    public List<RadioPostRequestDto> getRadios() {
        return radios;
    }
}
