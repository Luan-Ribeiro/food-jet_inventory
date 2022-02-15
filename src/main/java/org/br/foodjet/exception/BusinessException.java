package org.br.foodjet.exception;

import org.br.foodjet.resource.to.ErrorDetailTO;

public class BusinessException extends RuntimeException {

    ErrorDetailTO error;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, ErrorDetailTO errorDTO) {
        super(message);
        this.error = errorDTO;
    }

    public BusinessException(Throwable throwable) {
        super(throwable);

        if (throwable instanceof BusinessException) {
            this.error = ((BusinessException) throwable).getErrors();
        }
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorDetailTO getErrors() {
        return error;
    }
}