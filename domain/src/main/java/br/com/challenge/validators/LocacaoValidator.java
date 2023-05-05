package br.com.challenge.validators;

import br.com.challenge.domain.Locacao;
import br.com.challenge.exception.LocacaoServiceException;

/**
 * Validator para campos do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
public class LocacaoValidator {

    public static final String DATA_LOCACAO_INVALIDA = "Data de inicio nao pode ser maior que data final";
    public static final String LOCACAO_EM_ABERTO = "Cliente %s ja possui uma locacao em aberto";
    public static final String LOCACAO_NAO_ENCONTRADA = "Nao foi encontrada locacao com o id informado";
    public static final String LOCACAO_FINALIZADA = "Locacao ja esta finalizada";

    public static void validarDataLocacao(Locacao locacao) throws LocacaoServiceException {
        if (locacao.getDataInicio().isAfter(locacao.getDataFinal())) {
            throw new LocacaoServiceException(DATA_LOCACAO_INVALIDA);
        }
    }

}
