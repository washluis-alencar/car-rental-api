package br.com.challenge.presentation;

import br.com.challenge.domain.Locacao;
import br.com.challenge.exception.EntityNotFoundException;
import br.com.challenge.exception.LocacaoServiceException;
import br.com.challenge.presentation.representation.CalculoLocacaoRequest;
import br.com.challenge.presentation.representation.LocacaoRequest;
import br.com.challenge.service.LocacaoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Controller responsavel por expor os servicos relacionados ao dominio de {@link br.com.challenge.domain.Locacao}
 * @author Wahington Luis
 */
@RestController
@RequestMapping(path = ApiPath.LOCACOES)
public class LocacaoController {

    private final LocacaoService locacaoService;

    public LocacaoController(LocacaoService locacaoService) {
        this.locacaoService = locacaoService;
    }

    @PostMapping
    public Locacao realizarLocacao(@RequestBody @Valid LocacaoRequest locacaoRequest) throws LocacaoServiceException {
        Locacao locacao = Locacao.builder()
                .nomeCliente(locacaoRequest.getNomeCliente())
                .dataInicio(locacaoRequest.getDataInicial())
                .dataFinal(locacaoRequest.getDataFinal()).build();
        return locacaoService.realizarLocacao(locacao);
    }

    @GetMapping(path = "/{id}")
    public Locacao buscarLocacaoPorId(@PathVariable String id) throws EntityNotFoundException {
        return locacaoService.buscarLocacaoPorId(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping(path = "/aberta")
    public Locacao buscarLocacao(@RequestParam @NotEmpty String cliente) throws EntityNotFoundException {
        return locacaoService.buscarLocacaoEmAbertoPorCliente(cliente).orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping(path = "/cancelamento/{id}")
    public Locacao cancelarLocacao(@PathVariable String id) throws LocacaoServiceException {
        return locacaoService.cancelarLocacao(id);
    }

    @PutMapping(path = "/encerramento/{id}")
    public Locacao encerrarLocacao(@PathVariable String id) throws LocacaoServiceException {
        return locacaoService.encerrarLocacao(id);
    }

    @PostMapping(path = "/dias-restantes")
    public Locacao calcularDiasRestantes(@RequestBody @Valid CalculoLocacaoRequest locacaoRequest) throws LocacaoServiceException {
        return locacaoService.calcularDiasRestantes(locacaoRequest.getIdLocacao());
    }
}
