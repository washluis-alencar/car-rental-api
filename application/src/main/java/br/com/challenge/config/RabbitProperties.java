package br.com.challenge.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RabbitProperties {
    private String exchange;
    private String routerKey;
}
