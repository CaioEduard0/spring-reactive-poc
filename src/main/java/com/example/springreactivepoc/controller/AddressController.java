package com.example.springreactivepoc.controller;

import com.example.springreactivepoc.entity.Address;
import com.example.springreactivepoc.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
class AddressController {

    private final AddressService addressService;

    @GetMapping("addresses/{cep}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Address> getAddress(@PathVariable final String cep) {
        return addressService.getAddress(cep);
    }
}