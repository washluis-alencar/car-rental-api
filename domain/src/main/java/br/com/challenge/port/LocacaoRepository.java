package br.com.challenge.port;


import br.com.challenge.domain.Locacao;

import java.util.Optional;

/**
 * Interface que define os metodos para acesso
 * ao repositorio de dados do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
public interface LocacaoRepository {

    Locacao save(Locacao locacao);

    Optional<Locacao> buscarLocacaoEmAberto(String nomeCliente);

    Optional<Locacao> buscarLocacaoPorId(String id);
}
