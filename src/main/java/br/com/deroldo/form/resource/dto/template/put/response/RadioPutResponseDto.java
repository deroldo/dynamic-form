package br.com.deroldo.form.resource.dto.template.put.response;

import br.com.deroldo.form.entity.Radio;

public class RadioPutResponseDto {

    private String label;

    private String value;

    public RadioPutResponseDto() {
    }

    public RadioPutResponseDto(final Radio radio) {
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
