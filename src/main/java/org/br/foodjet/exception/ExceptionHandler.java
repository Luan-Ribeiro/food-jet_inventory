package org.br.foodjet.exception;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.status;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.exception.to.ErrorDetailTO;

@Slf4j
public abstract class ExceptionHandler<T extends Throwable> implements ExceptionMapper<T> {

    @Override
    public Response toResponse(Throwable exception) {
        return getResponse(exception);
    }

    protected Response getResponse(Throwable throwable) {
        logException(throwable);

        var responseStatus = getResponseStatus();
        var errorDetail = buildErrorDetail(responseStatus, throwable);

        return buildResponse(responseStatus, errorDetail);
    }

    protected Response buildResponse(Status responseStatus, ErrorDetailTO errorDetail) {
        return status(responseStatus).entity(errorDetail).build();
    }

    protected void logException(Throwable throwable) {
        var throwableClassName = throwable.getClass().getName();
        log.error("[".concat(throwableClassName).concat("]"), throwable);
    }

    protected Status getResponseStatus() {
        return INTERNAL_SERVER_ERROR;
    }

    protected ErrorDetailTO buildErrorDetail(Status status, Throwable throwable) {

        var errorDetail = new ErrorDetailTO();
        errorDetail.setCode(String.valueOf(status.getStatusCode()));
        errorDetail.setMessage(throwable.getMessage());

        return errorDetail;
    }
}