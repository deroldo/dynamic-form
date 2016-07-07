package br.com.deroldo.form.exception.bean;

public class InvalidField {

    private String message;
    private String category;

    public InvalidField() {
    }

    public InvalidField(final String message, final String category) {
        this.message = message;
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public String getCategory() {
        return category;
    }
}
