package br.com.challenge.adapter.repository;

import br.com.challenge.domain.Locacao;
import br.com.challenge.entities.LocacaoEntity;
import br.com.challenge.infrastracture.repository.LocacaoDataRepository;
import br.com.challenge.mapper.LocacaoMapper;
import br.com.challenge.port.LocacaoRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Classe adapter do repositorio do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
@Component
public class LocacaoRepositoryAdapter implements LocacaoRepository {

    private final LocacaoDataRepository repository;
    private final LocacaoMapper mapper = Mappers.getMapper(LocacaoMapper.class);

    public LocacaoRepositoryAdapter(LocacaoDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public Locacao save(Locacao locacao) {
        LocacaoEntity entity = mapper.modelToEntity(locacao);
        return mapper.entityToModel(repository.save(entity));
    }

    @Override
    public Optional<Locacao> buscarLocacaoEmAberto(String nomeCliente) {
        Optional<LocacaoEntity> locacaoEntity = repository.findByNomeClienteAndDataFechamentoNull(
                nomeCliente.trim().toUpperCase());
        return locacaoEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<Locacao> buscarLocacaoPorId(String id) {
        Optional<LocacaoEntity> locacaoEntity = repository.findById(id);
        return locacaoEntity.map(mapper::entityToModel);
    }
}
