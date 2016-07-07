package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.entity.FieldType;
import br.com.deroldo.form.entity.Template;
import br.com.deroldo.form.entity.TemplateField;
import br.com.deroldo.form.exception.ValidationExcetion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TemplateValidatorImplTest {

    @Test
    public void cant_throw_exception(){
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("teste", FieldType.TEXT, true, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        new TemplateValidatorImpl().validate(template);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_excetion_when_radio_values_not_found(){
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("teste", FieldType.RADIO, true, false, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        new TemplateValidatorImpl().validate(template);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_excetion_when_read_only_value_not_found(){
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("teste", FieldType.TEXT, true, true, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        new TemplateValidatorImpl().validate(template);
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_excetion_when_type_not_found(){
        final List<TemplateField> fields = new ArrayList<>();
        final TemplateField field = new TemplateField("teste", null, true, true, null, null, null, null);
        fields.add(field);
        final Template template = new Template("teste", fields);
        new TemplateValidatorImpl().validate(template);
    }

}