package br.com.challenge.service.impl;

import br.com.challenge.domain.Locacao;
import br.com.challenge.enums.MotivoFechamentoEnum;
import br.com.challenge.exception.LocacaoServiceException;
import br.com.challenge.port.LocacaoRepository;
import br.com.challenge.service.LocacaoService;
import br.com.challenge.validators.LocacaoValidator;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

/**
 * Classe que implementa os metodos de negocio do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
@Slf4j
public class LocacaoServiceImpl implements LocacaoService {

    private final LocacaoRepository locacaoRepository;

    public LocacaoServiceImpl(LocacaoRepository locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    @Override
    public Locacao realizarLocacao(Locacao locacao) throws LocacaoServiceException {
        log.info("Iniciando nova locacao");

        LocacaoValidator.validarDataLocacao(locacao);

        locacao.setNomeCliente(locacao.getNomeCliente().trim().toUpperCase());

        Optional<Locacao> locacaoAberta = locacaoRepository.buscarLocacaoEmAberto(locacao.getNomeCliente());
        if (locacaoAberta.isPresent()) {
            throw new LocacaoServiceException(String.format(LocacaoValidator.LOCACAO_EM_ABERTO, locacao.getNomeCliente()));
        }

        log.info("Salvando locacao");
        return locacaoRepository.save(locacao);
    }

    @Override
    public Optional<Locacao> buscarLocacaoPorId(String id) {
        return locacaoRepository.buscarLocacaoPorId(id);
    }

    @Override
    public Optional<Locacao> buscarLocacaoEmAbertoPorCliente(String cliente) {
        return this.locacaoRepository.buscarLocacaoEmAberto(cliente);
    }

    @Override
    public Locacao cancelarLocacao(String id) throws LocacaoServiceException {
        log.info("Cancelando locacao com id {}", id);

        final Locacao locacaoCancelar = validarFechamentoLocacao(id);
        locacaoCancelar.setDataFechamento(LocalDateTime.now());
        locacaoCancelar.setMotivoFechamento(MotivoFechamentoEnum.CANCELAMENTO);

        return locacaoRepository.save(locacaoCancelar);
    }

    @Override
    public Locacao encerrarLocacao(String id) throws LocacaoServiceException {
        log.info("Encerrando locacao com id {}", id);

        final Locacao locacaoEncerrar = validarFechamentoLocacao(id);
        locacaoEncerrar.setDataFechamento(LocalDateTime.now());
        locacaoEncerrar.setMotivoFechamento(MotivoFechamentoEnum.ENTREGA_VEICULO);

        return locacaoRepository.save(locacaoEncerrar);
    }

    @Override
    public Locacao calcularDiasRestantes(String idLocacao) throws LocacaoServiceException {
        Optional<Locacao> locacaoOptional = this.locacaoRepository.buscarLocacaoPorId(idLocacao);
        Locacao locacao = locacaoOptional.orElseThrow(() -> new LocacaoServiceException(LocacaoValidator.LOCACAO_NAO_ENCONTRADA));
        long days = Duration.between(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS),
                locacao.getDataFinal().truncatedTo(ChronoUnit.DAYS))
                .toDays();
        locacao.setQtdDiasRestantes(days > 0 ? (int) days : 0);
        return locacao;
    }

    private Locacao validarFechamentoLocacao(String id) throws LocacaoServiceException {
        Optional<Locacao> locacao = this.buscarLocacaoPorId(id);
        if (locacao.isEmpty()) {
            throw new LocacaoServiceException(LocacaoValidator.LOCACAO_NAO_ENCONTRADA);
        }
        final Locacao locacaoCancelar = locacao.get();
        if (locacaoCancelar.getDataFechamento() != null) {
            throw new LocacaoServiceException(LocacaoValidator.LOCACAO_FINALIZADA);
        }
        return locacaoCancelar;
    }

}
