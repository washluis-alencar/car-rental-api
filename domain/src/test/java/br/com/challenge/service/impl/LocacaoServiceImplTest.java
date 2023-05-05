package br.com.challenge.service.impl;

import br.com.challenge.domain.Locacao;
import br.com.challenge.enums.MotivoFechamentoEnum;
import br.com.challenge.exception.LocacaoServiceException;
import br.com.challenge.port.LocacaoRepository;
import br.com.challenge.service.LocacaoService;
import br.com.challenge.validators.LocacaoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Classe de teste para service do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
class LocacaoServiceImplTest {

    private LocacaoRepository locacaoRepository;
    private LocacaoService locacaoService;

    @BeforeEach
    void setUp() {
        this.locacaoRepository = Mockito.mock(LocacaoRepository.class);
        this.locacaoService = new LocacaoServiceImpl(locacaoRepository);
    }

    @Test
    void testRealizarLocacaoSucesso() throws LocacaoServiceException {
        Locacao locacao = mockLocacaoSemId();
        Mockito.when(locacaoRepository.save(Mockito.any())).thenReturn(mockLocacaoComId());
        Locacao locacaoSalva = locacaoService.realizarLocacao(locacao);
        Assertions.assertNotNull(locacaoSalva);
        Assertions.assertNotNull(locacaoSalva.getId());
    }

    @Test
    void testRealizarLocacaoDataInicioMaiorDataFinal() {
        Locacao locacao = mockLocacaoSemId();
        locacao.setDataFinal(locacao.getDataInicio().minusDays(1));
        LocacaoServiceException exception = Assertions.assertThrows(LocacaoServiceException.class, () -> locacaoService.realizarLocacao(locacao));
        Assertions.assertEquals(LocacaoValidator.DATA_LOCACAO_INVALIDA, exception.getLocalizedMessage());
    }

    @Test
    void testRealizarLocacaoComOutraEmAberto() {
        Locacao locacao = mockLocacaoSemId();
        Optional<Locacao> locacaoEmAberto = Optional.of(mockLocacaoComId());
        Mockito.when(locacaoRepository.buscarLocacaoEmAberto(Mockito.anyString())).thenReturn(locacaoEmAberto);
        LocacaoServiceException exception = Assertions.assertThrows(LocacaoServiceException.class, () -> locacaoService.realizarLocacao(locacao));
        Assertions.assertEquals(
                String.format(LocacaoValidator.LOCACAO_EM_ABERTO, locacaoEmAberto.get().getNomeCliente()),
                exception.getLocalizedMessage());
    }

    @Test
    void testbuscarLocacaoPorIdSucesso() {
        Optional<Locacao> locacao = Optional.of(mockLocacaoComId());
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(locacao);
        Optional<Locacao> locacaoCadatrada = locacaoService.buscarLocacaoPorId(locacao.get().getId());

        Assertions.assertNotNull(locacaoCadatrada);
        Assertions.assertTrue(locacaoCadatrada.isPresent());
        Assertions.assertEquals(locacaoCadatrada.get(), locacao.get());
    }

    @Test
    void testBuscarLocacaoEmAbertoPorClienteSucesso() {
        Optional<Locacao> locacao = Optional.of(mockLocacaoComId());
        Mockito.when(locacaoRepository.buscarLocacaoEmAberto(Mockito.anyString())).thenReturn(locacao);
        Optional<Locacao> locacaoCadatrada = locacaoService.buscarLocacaoEmAbertoPorCliente(locacao.get().getNomeCliente());

        Assertions.assertNotNull(locacaoCadatrada);
        Assertions.assertTrue(locacaoCadatrada.isPresent());
        Assertions.assertEquals(locacaoCadatrada.get(), locacao.get());
    }

    @Test
    void testCancelarLocacaoSucesso() throws LocacaoServiceException {
        Locacao locacao = mockLocacaoComId();
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacao));

        Locacao locacaoComFechamento = mockLocacaoComFechamento(MotivoFechamentoEnum.CANCELAMENTO);
        Mockito.when(locacaoRepository.save(Mockito.any())).thenReturn(locacaoComFechamento);

        Locacao locacaoCancelada = locacaoService.cancelarLocacao(locacao.getId());

        Assertions.assertNotNull(locacaoCancelada);
        Assertions.assertEquals(MotivoFechamentoEnum.CANCELAMENTO, locacaoCancelada.getMotivoFechamento());
    }

    @Test
    void testCancelarLocacaoFinalizada() {
        Locacao locacao = mockLocacaoComFechamento(MotivoFechamentoEnum.CANCELAMENTO);
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacao));

        LocacaoServiceException exception = Assertions.assertThrows(
                LocacaoServiceException.class, () ->locacaoService.cancelarLocacao(locacao.getId()));

        Assertions.assertEquals(LocacaoValidator.LOCACAO_FINALIZADA, exception.getLocalizedMessage());
    }

    @Test
    void testCancelarLocacaoInexistente() {
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.empty());

        LocacaoServiceException exception = Assertions.assertThrows(
                LocacaoServiceException.class, () ->locacaoService.cancelarLocacao("123123"));

        Assertions.assertEquals(LocacaoValidator.LOCACAO_NAO_ENCONTRADA, exception.getLocalizedMessage());
    }

    @Test
    void testEncerrarLocacaoFinalizada() {
        Locacao locacao = mockLocacaoComFechamento(MotivoFechamentoEnum.ENTREGA_VEICULO);
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacao));

        LocacaoServiceException exception = Assertions.assertThrows(
                LocacaoServiceException.class, () ->locacaoService.encerrarLocacao(locacao.getId()));

        Assertions.assertEquals(LocacaoValidator.LOCACAO_FINALIZADA, exception.getLocalizedMessage());
    }

    @Test
    void testEncerrarLocacaoSucesso() throws LocacaoServiceException {
        Locacao locacao = mockLocacaoComId();
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacao));

        Locacao locacaoComFechamento = mockLocacaoComFechamento(MotivoFechamentoEnum.ENTREGA_VEICULO);
        Mockito.when(locacaoRepository.save(Mockito.any())).thenReturn(locacaoComFechamento);

        Locacao locacaoEncerrada = locacaoService.encerrarLocacao(locacao.getId());

        Assertions.assertNotNull(locacaoEncerrada);
        Assertions.assertEquals(MotivoFechamentoEnum.ENTREGA_VEICULO, locacaoEncerrada.getMotivoFechamento());
    }

    @Test
    void testEncerrarLocacaoInexistente() {
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.empty());

        LocacaoServiceException exception = Assertions.assertThrows(
                LocacaoServiceException.class, () ->locacaoService.encerrarLocacao("123123"));

        Assertions.assertEquals(LocacaoValidator.LOCACAO_NAO_ENCONTRADA, exception.getLocalizedMessage());
    }

    @Test
    void testCalcularDiasRestantesSucesso() throws LocacaoServiceException {
        Locacao locacaoMock = mockLocacaoComId();
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacaoMock));

        Locacao locacao = locacaoService.calcularDiasRestantes(locacaoMock.getId());
        Assertions.assertEquals(5, locacao.getQtdDiasRestantes());
    }

    @Test
    void testCalcularDiasRestantesComLocacaoVencida() throws LocacaoServiceException {
        Locacao locacaoMock = mockLocacaoComId();
        locacaoMock.setDataInicio(LocalDateTime.now().minusDays(2));
        locacaoMock.setDataFinal(LocalDateTime.now().minusDays(1));
        Mockito.when(locacaoRepository.buscarLocacaoPorId(Mockito.anyString())).thenReturn(Optional.of(locacaoMock));

        Locacao locacao = locacaoService.calcularDiasRestantes(locacaoMock.getId());
        Assertions.assertEquals(0, locacao.getQtdDiasRestantes());
    }

    private Locacao mockLocacaoSemId() {
        return Locacao.builder().nomeCliente("CLIENTE TESTE")
                .dataInicio(LocalDateTime.now())
                .dataFinal(LocalDateTime.now().plusDays(5)).build();
    }


    private Locacao mockLocacaoComId() {
        return mockLocacaoSemId().toBuilder()
                .id("123123").build();
    }

    private Locacao mockLocacaoComFechamento(MotivoFechamentoEnum motivoFechamento) {
        return mockLocacaoComId().toBuilder()
                .dataFechamento(LocalDateTime.now())
                .motivoFechamento(motivoFechamento).build();
    }
}
