package br.com.challenge.entities;

import br.com.challenge.enums.MotivoFechamentoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(value = "locacao")
public class LocacaoEntity {

    @Id
    private String id;

    @Field
    @Indexed
    private String nomeCliente;

    @Field
    private LocalDateTime dataInicio;

    @Field
    private LocalDateTime dataFinal;

    @Field
    private LocalDateTime dataFechamento;

    @Field
    private MotivoFechamentoEnum motivoFechamento;

    @Field
    private Integer qtdDiasRestantes;
}
