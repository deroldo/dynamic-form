package br.com.deroldo.form.resource.dto.template.put.request;

import org.hibernate.validator.constraints.NotBlank;

public class RadioPutRequestDto {

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
