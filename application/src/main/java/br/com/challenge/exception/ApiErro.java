package br.com.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Classe para representar retorno customizado de erro nas requisicoes
 * @author Washington Luis
 */
@Data
@AllArgsConstructor
@Builder
public class ApiErro {

    private String message;

    private LocalDateTime timeStamp;

    private String path;

    private Integer status;
}
