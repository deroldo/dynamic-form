package br.com.deroldo.form.entity;

import org.hibernate.validator.constraints.NotBlank;

public class Radio {

    @NotBlank
    private String label;

    @NotBlank
    private String value;

    public Radio(final String label, final String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
