package org.itech.iframework.domain;

import org.itech.iframework.AbstractException;

/**
 * DomainException
 *
 * @author liuqiang
 */
public class DomainException extends AbstractException {
    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(Throwable cause) {
        super(cause);
    }
}
