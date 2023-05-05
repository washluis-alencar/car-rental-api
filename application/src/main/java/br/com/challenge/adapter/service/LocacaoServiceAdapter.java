package br.com.challenge.adapter.service;

import br.com.challenge.adapter.jms.CalculoDiasLocacaoSenderAdapter;
import br.com.challenge.domain.Locacao;
import br.com.challenge.exception.LocacaoServiceException;
import br.com.challenge.port.LocacaoRepository;
import br.com.challenge.service.impl.LocacaoServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Classe adapter do servico do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
@Service
public class LocacaoServiceAdapter extends LocacaoServiceImpl {

    private final CalculoDiasLocacaoSenderAdapter calculoDiasLocacaoSenderAdapter;

    public LocacaoServiceAdapter(LocacaoRepository locacaoRepository,
                                 CalculoDiasLocacaoSenderAdapter calculoDiasLocacaoSenderAdapter) {
        super(locacaoRepository);
        this.calculoDiasLocacaoSenderAdapter = calculoDiasLocacaoSenderAdapter;
    }

    @Override
    public Locacao calcularDiasRestantes(String idLocacao) throws LocacaoServiceException {
        Locacao locacao = super.calcularDiasRestantes(idLocacao);
        this.calculoDiasLocacaoSenderAdapter.sendCalculoDiasRestantes(locacao);
        return locacao;
    }
}
