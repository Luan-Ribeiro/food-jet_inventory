package org.br.foodjet.exception.handler;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.exception.ExceptionHandler;

@Provider
@Slf4j
public class BusinessExceptionHandler extends ExceptionHandler<BusinessException> {

    @Override
    protected Response.Status getResponseStatus() {
        return BAD_REQUEST;
    }

    @Override
    protected void logException(Throwable throwable) {
        var throwableClassName = throwable.getClass().getName();
        log.error("[{}] {}", throwableClassName, throwable.getMessage());
    }
}
