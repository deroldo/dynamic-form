package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import br.com.deroldo.form.exception.ValidationExcetion;
import br.com.deroldo.form.validator.TemplateDataValidator;
import br.com.deroldo.form.validator.TemplateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TemplateDataValidatorImpl implements TemplateDataValidator {

    private static final String INVALID_FIELD = "Field is invalid";
    private static final String REQUIRED_FIELD = "Field is requerid";
    private static final String READ_ONLY_FIELD = "Field is readOnly";
    private static final String INVALID_TYPE_FIELD = "Field's value is invalid";
    private static final String MAX_LENGTH_FIELD = "Field's max length is %d";

    @Autowired
    private TemplateValidator validator;

    @Override
    public void validate(final Template template, final Map<String, String> params) {
        this.validator.validate(template);

        final Map<String, String> mapParams = Optional.ofNullable(params).orElse(new HashMap<>());
        final Map<String, List<String>> errors = new HashMap<>();

        template.getFields().forEach(field -> {
            final Optional<String> dataValue = mapParams.keySet().stream()
                    .filter(key -> field.getLabel().equalsIgnoreCase(key))
                    .findFirst();

            if (requiredNotFound(field, dataValue)){
                addErrorList(errors, REQUIRED_FIELD, field.getLabel());

            } else if (readOnlyPresent(field, dataValue)){
                addErrorList(errors, READ_ONLY_FIELD, field.getLabel());

            } else if (higherMaxLength(field, mapParams, dataValue)){
                final String message = String.format(MAX_LENGTH_FIELD, field.getMaxLength());
                addErrorList(errors, message, dataValue.get());

            } else if (invalidTypeValue(field, mapParams, dataValue)){
                addErrorList(errors, INVALID_TYPE_FIELD, dataValue.get());
            }

        });

        mapParams.keySet().stream()
                .filter(key -> template.getFields().stream().noneMatch(field -> field.getLabel().equalsIgnoreCase(key)))
                .forEach(label -> addErrorList(errors, INVALID_FIELD, label));

        if (!errors.isEmpty()){
            throw new ValidationExcetion(errors);
        }
    }

    private boolean invalidTypeValue(final TemplateField field, final Map<String, String> mapParams, final Optional<String> dataValue) {
        final FieldType fieldType = field.getType();
        if (fieldType.getRegex() == null || !dataValue.isPresent()){
            return false;
        }
        return !mapParams.get(dataValue.get()).matches(fieldType.getRegex());
    }

    private void addErrorList(final Map<String, List<String>> errors, final String key, final String label) {
        final List<String> readOnlyErrorList = Optional.ofNullable(errors.get(key)).orElse(new ArrayList<>());
        readOnlyErrorList.add(label);
        errors.put(key, readOnlyErrorList);
    }

    private boolean higherMaxLength(final TemplateField field, final Map<String, String> mapParams, final Optional<String> dataValue) {
        return field.getMaxLength() != null && dataValue.isPresent() && mapParams.get(dataValue.get()).length() > field.getMaxLength();
    }

    private boolean readOnlyPresent(final TemplateField field, final Optional<String> dataValue) {
        return field.isReadOnly() && dataValue.isPresent();
    }

    private boolean requiredNotFound(final TemplateField field, final Optional<String> dataValue) {
        return field.isRequired() && !field.isReadOnly() && !dataValue.isPresent();
    }
}
