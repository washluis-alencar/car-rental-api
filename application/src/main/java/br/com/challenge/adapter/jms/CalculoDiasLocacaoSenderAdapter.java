package br.com.challenge.adapter.jms;

import br.com.challenge.config.RabbitProperties;
import br.com.challenge.domain.Locacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculoDiasLocacaoSenderAdapter {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public CalculoDiasLocacaoSenderAdapter(RabbitTemplate rabbitTemplate,
                                           RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    public void sendCalculoDiasRestantes(Locacao locacao) {
        log.info("Enviando mensagem de calculo de dias para locacao {}", locacao.getId());

        this.rabbitTemplate.convertAndSend(rabbitProperties.getExchange(), rabbitProperties.getRouterKey(), locacao);

        log.info("Mensagem de calculo de dias para locacao {} enviada", locacao.getId());
    }


}
