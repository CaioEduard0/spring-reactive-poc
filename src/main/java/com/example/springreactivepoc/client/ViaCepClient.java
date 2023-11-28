package com.example.springreactivepoc.client;

import com.example.springreactivepoc.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ViaCepClient {

    private final String url;

    public ViaCepClient(@Value("${viacep.url}") final String url) {
        this.url = url;
    }

    public Mono<Address> fetchAddress(final String cep) {

        final WebClient webClient = WebClient.builder()
                                             .baseUrl(String.format(url, cep))
                                             .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                             .build();
        return webClient.get().exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) return response.bodyToMono(Address.class);
            log.error("Erro ao fazer a requisição para a api viacep.");
            return null;
        });
    }
}