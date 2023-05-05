package br.com.challenge.mapper;

import br.com.challenge.domain.Locacao;
import br.com.challenge.entities.LocacaoEntity;
import org.mapstruct.Mapper;

/**
 * Mapper para converter objetos do dominio de {@link br.com.challenge.domain.Locacao} em objetos de entidade
 * @author Washingon Luis
 */
@Mapper
public interface LocacaoMapper {

    LocacaoEntity modelToEntity(Locacao locacao);

    Locacao entityToModel(LocacaoEntity locacaoEntity);
}
