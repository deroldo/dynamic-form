package br.com.deroldo.form.validator.impl;

import br.com.deroldo.form.exception.ValidationExcetion;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;

public class ContractValidatorImplTest {

    @Test
    public void cant_throw_exception(){
        new ContractValidatorImpl().validate(new TestValidator("xpto"));
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_exception(){
        new ContractValidatorImpl().validate(new TestValidator(""));
    }

    @Test(expected = ValidationExcetion.class)
    public void should_throw_exception_when_null_parameter(){
        new ContractValidatorImpl().validate(null);
    }

    public class TestValidator {
        @NotBlank
        private String xpto;
        public TestValidator(String xpto){
            this.xpto = xpto;
        }
    }

}