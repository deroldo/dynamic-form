package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import br.com.deroldo.form.exception.ValidationExcetion;
import br.com.deroldo.form.validator.TemplateValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TemplateValidatorImpl implements TemplateValidator {

    private static final String INVALID_TYPE = "Invalid field type";
    private static final String READ_ONLY = "Read only field should have value";
    private static final String RADIO = "Radio type should have radios value";

    @Override
    public void validate(final Template template) {
        final Map<String, List<String>> errors = new HashMap<>();
        errors.putAll(fieldTypeValidate(template));
        errors.putAll(radioFieldValidate(template));
        errors.putAll(readOnlyValidate(template));

        if (!errors.isEmpty()){
            throw new ValidationExcetion(errors);
        }
    }

    private Map<String, List<String>> fieldTypeValidate(final Template template) {
        final List<String> invalidFields = template.getFields().stream()
                .filter(field -> field.getType() == null)
                .map(TemplateField::getLabel)
                .collect(Collectors.toList());

        return getMap(INVALID_TYPE, invalidFields);
    }

    private Map<String, List<String>> readOnlyValidate(final Template template) {
        final List<String> invalidFields = template.getFields().stream()
                .filter(field -> field.isReadOnly())
                .filter(field -> StringUtils.isBlank(field.getValue()))
                .map(TemplateField::getLabel)
                .collect(Collectors.toList());

        return getMap(READ_ONLY, invalidFields);
    }

    private Map<String, List<String>> radioFieldValidate(final Template template) {
        final List<String> invalidFields = template.getFields().stream()
                .filter(field -> FieldType.RADIO.equals(field.getType()))
                .filter(field -> CollectionUtils.isEmpty(field.getRadios()))
                .map(TemplateField::getLabel)
                .collect(Collectors.toList());

        return getMap(RADIO, invalidFields);
    }

    private Map<String,List<String>> getMap(final String message, final List<String> invalidFields) {
        final Map<String,List<String>> map = new HashMap<>();
        if (!invalidFields.isEmpty()){
            map.put(message, invalidFields);
        }
        return map;
    }

}
