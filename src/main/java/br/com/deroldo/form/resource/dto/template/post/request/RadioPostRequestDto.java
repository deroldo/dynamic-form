package br.com.deroldo.form.resource.dto.template.post.request;

import org.hibernate.validator.constraints.NotBlank;

public class RadioPostRequestDto {

    @NotBlank
    private String label;

    @NotBlank
    private String value;

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
