package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.exception.ValidationExcetion;
import br.com.deroldo.form.validator.ContractValidator;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;

@Component
public class ContractValidatorImpl implements ContractValidator {

    @Override
    public void validate(final Object object) {
        final Map<String, List<String>> errors = new HashMap<>();

        if (object == null){
            errors.put("Invalid null object", Arrays.asList("object"));
        } else {
            final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            final javax.validation.Validator validator = factory.getValidator();
            final Set<ConstraintViolation<Object>> validate = validator.validate(object);

            validate.forEach(constraint -> {
                final String message = constraint.getMessage();
                final String field = constraint.getPropertyPath().toString();
                final List<String> fields = Optional.ofNullable(errors.get(message)).orElse(new ArrayList<>());
                fields.add(field);
                errors.put(message, fields);
            });
        }

        if (!errors.isEmpty()){
            throw new ValidationExcetion(errors);
        }
    }

}
