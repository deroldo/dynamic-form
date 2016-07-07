package br.com.deroldo.form.validator;

import br.com.deroldo.form.entity.Template;

import java.util.Map;

public interface TemplateDataValidator {

    void validate(Template template, Map<String, String> params);
}
