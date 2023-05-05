package br.com.challenge.exception;

/**
 * Classe base para exception de services
 * @author Washington Luis
 */
public class BaseServiceException extends Exception {
    public BaseServiceException(String message) {
        super(message);
    }
}
