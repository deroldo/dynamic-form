package br.com.deroldo.form.exception.response;

import org.springframework.http.HttpStatus;

public class GenericError {

    private boolean error = true;

    private String message;

    private int statusCode;

    public GenericError() {
    }

    public GenericError(final String message, final HttpStatus httpStatus) {
        this.message = message;
        this.statusCode = httpStatus.value();
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
