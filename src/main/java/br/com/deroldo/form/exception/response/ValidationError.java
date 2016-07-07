package br.com.deroldo.form.exception.response;

import br.com.deroldo.form.exception.bean.InvalidField;

import java.util.List;

public class ValidationError {

    private List<InvalidField> errors;

    public ValidationError() {
    }

    public ValidationError(final List<InvalidField> errors) {
        this.errors = errors;
    }

    public List<InvalidField> getErrors() {
        return errors;
    }
}
