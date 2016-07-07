package br.com.deroldo.form.exception;

import br.com.deroldo.form.exception.bean.InvalidField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationExcetion extends RuntimeException {

    private List<InvalidField> errors;

    public ValidationExcetion(final Map<String, List<String>> errors) {
        this.errors = new ArrayList<>();
        errors.forEach((k, v) -> {
            v.forEach(fieldLabel -> {
                this.errors.add(new InvalidField(k, fieldLabel));
            });
        });
    }

    public List<InvalidField> getErrors() {
        return errors;
    }
}
