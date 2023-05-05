package br.com.challenge.infrastracture.repository;

import br.com.challenge.domain.Locacao;
import br.com.challenge.entities.LocacaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repositorio para o dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
@Repository
public interface LocacaoDataRepository extends MongoRepository<LocacaoEntity, String> {

    Optional<LocacaoEntity> findByNomeClienteAndDataFechamentoNull(String nomeCliente);
}
