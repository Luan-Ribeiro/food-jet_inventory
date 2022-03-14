package org.br.foodjet.exception.handler;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.google.common.base.Throwables;
import io.quarkus.arc.ArcUndeclaredThrowableException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.br.foodjet.exception.ExceptionHandler;

@Provider
public class ConstraintViolationExceptionHandler extends ExceptionHandler<ArcUndeclaredThrowableException> {

    @Override
    protected Response getResponse(Throwable throwable) {

        return super.getResponse(Throwables.getRootCause(throwable));
    }

    @Override
    protected Status getResponseStatus() {
        return INTERNAL_SERVER_ERROR;
    }
}