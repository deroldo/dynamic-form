package br.com.deroldo.form.resource.dto.template.get.id.response;

import br.com.deroldo.form.entity.Radio;

public class RadioGetResponseDto {

    private String label;

    private String value;

    public RadioGetResponseDto() {
    }

    public RadioGetResponseDto(final Radio radio) {
        this.label = radio.getLabel();
        this.value = radio.getValue();
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
