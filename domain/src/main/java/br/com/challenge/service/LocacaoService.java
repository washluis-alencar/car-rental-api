package br.com.challenge.service;

import br.com.challenge.domain.Locacao;
import br.com.challenge.exception.LocacaoServiceException;

import java.util.Optional;

/**
 * Interface que define os metodos de negocio do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
public interface LocacaoService {

    Locacao realizarLocacao(Locacao locacao) throws LocacaoServiceException;

    Optional<Locacao> buscarLocacaoPorId(String id);

    Optional<Locacao> buscarLocacaoEmAbertoPorCliente(String cliente);

    Locacao cancelarLocacao(String id) throws LocacaoServiceException;

    Locacao encerrarLocacao(String id) throws LocacaoServiceException;

    Locacao calcularDiasRestantes(String idLocacao) throws LocacaoServiceException;
}
