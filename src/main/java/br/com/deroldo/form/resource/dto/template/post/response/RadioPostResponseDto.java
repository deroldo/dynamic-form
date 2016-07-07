package br.com.deroldo.form.resource.dto.template.post.response;

import br.com.deroldo.form.entity.Radio;

public class RadioPostResponseDto {

    private String label;

    private String value;

    public RadioPostResponseDto() {
    }

    public RadioPostResponseDto(final Radio radio) {
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
