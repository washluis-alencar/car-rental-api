package br.com.challenge.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Record para representar o request para servicos do dominio de {@link br.com.challenge.domain.Locacao}
 * @author Washington Luis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocacaoRequest {

    @NotBlank
    private String nomeCliente;

    @NotNull
    private LocalDateTime dataInicial;

    @NotNull
    private LocalDateTime dataFinal;
}
