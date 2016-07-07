package br.com.deroldo.form.config;

import br.com.deroldo.form.exception.NotFoundException;
import br.com.deroldo.form.exception.ValidationExcetion;
import br.com.deroldo.form.exception.response.GenericError;
import br.com.deroldo.form.exception.response.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerConfig {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public GenericError genericError(final NotFoundException e){
        return new GenericError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationExcetion.class)
    public ValidationError genericError(final ValidationExcetion e){
        return new ValidationError(e.getErrors());
    }

}
