package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import br.com.deroldo.form.exception.ValidationExcetion;
import br.com.deroldo.form.validator.TemplateDataValidator;
import br.com.deroldo.form.validator.TemplateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TemplateDataValidatorImplTest {

    @Mock
    private TemplateValidator validator;

    @InjectMocks
    private TemplateDataValidator validatorData = new TemplateDataValidatorImpl();

    @Test
    public void cant_throw_exception(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, true, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_error_required_not_present(){
        final Map<String, String> params = new HashMap<>();
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, true, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_error_read_only_sent(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, true, true, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_read_only_not_sent(){
        final Map<String, String> params = new HashMap<>();
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, true, true, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_error_max_lenght(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, false, false, 1, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_max_lenght_not_sent(){
        final Map<String, String> params = new HashMap<>();
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, false, false, 1, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_not_max_lenght(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, false, false, 100, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_error_invalid_field(){
        final Map<String, String> params = new HashMap<>();
        params.put("kkk", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.TEXT, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_error_invalid_field_value(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.EMAIL, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_with_valid_value(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "teste@teste.com");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.EMAIL, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void should_throw_error_empty_field_value(){
        final Map<String, String> params = new HashMap<>();
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.EMAIL, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_without_regex(){
        final Map<String, String> params = new HashMap<>();
        params.put("xpto", "value");
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.WEEK, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

    @Test
    public void cant_throw_exception_without_regex_and_value(){
        final Map<String, String> params = new HashMap<>();
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("xpto", FieldType.WEEK, false, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        this.validatorData.validate(template, params);
    }

}