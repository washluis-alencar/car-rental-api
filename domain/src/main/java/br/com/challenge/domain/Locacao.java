package br.com.challenge.domain;

import br.com.challenge.enums.MotivoFechamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe que representa o dominio de <code>Locacao</code>
 * @author Washington Luis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Locacao {
    private String id;
    private String nomeCliente;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private LocalDateTime dataFechamento;
    private MotivoFechamentoEnum motivoFechamento;
    private Integer qtdDiasRestantes;
}
