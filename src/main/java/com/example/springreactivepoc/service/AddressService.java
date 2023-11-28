package com.example.springreactivepoc.service;

import com.example.springreactivepoc.client.ViaCepClient;
import com.example.springreactivepoc.entity.Address;
import com.example.springreactivepoc.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AddressRepository addressRepository;

    public Mono<Address> getAddress(final String cep) {

        final Mono<Address> addressResponse = viaCepClient.fetchAddress(cep);
        return addressResponse.map(address -> {
            kafkaTemplate.send("address", address.toString());
            return addressRepository.save(address);
        }).flatMap(Function.identity());
    }

    @KafkaListener(topics = "address", groupId = "group-1")
    public void receive(final String message) {
        log.info("Consumer received message: {}", message);
    }
}