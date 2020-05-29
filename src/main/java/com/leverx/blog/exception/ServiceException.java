package com.leverx.blog.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {
    @Getter
    protected String code = "500";

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
