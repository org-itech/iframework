package org.itech.iframework;

/**
 * Abstract Exception
 *
 * @author liuqiang
 */
public abstract class AbstractException extends RuntimeException {
    public AbstractException() {
    }

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractException(Throwable cause) {
        super(cause);
    }
}
