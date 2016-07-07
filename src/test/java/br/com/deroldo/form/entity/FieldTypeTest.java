package br.com.deroldo.form.entity;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FieldTypeTest {

    @Test
    public void valueOf(){
        final FieldType email = FieldType.valueOf("EMAIL");
        assertNotNull(email);
    }

    @Test
    public void get(){
        final FieldType email = FieldType.get("email");
        assertNotNull(email);
    }

    @Test
    public void get_null(){
        final FieldType xpto = FieldType.get("xpto");
        assertNull(xpto);
    }

}