package br.com.challenge.exception;

/**
 * Exception lancada pelo service do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
public class LocacaoServiceException extends BaseServiceException {

    public LocacaoServiceException(String message) {
        super(message);
    }
}
