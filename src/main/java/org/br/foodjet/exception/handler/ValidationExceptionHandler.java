package org.br.foodjet.exception.handler;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.br.foodjet.exception.ExceptionHandler;

@Provider
public class ValidationExceptionHandler extends ExceptionHandler<ValidationException> {

    @Override
    protected Status getResponseStatus() {
        return BAD_REQUEST;
    }
}