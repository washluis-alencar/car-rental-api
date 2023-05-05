package br.com.challenge.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Classe para representar requisicao do calculo de dias restantes
 * @author Washington Luis
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculoLocacaoRequest {

    @NotBlank
    private String idLocacao;
}
