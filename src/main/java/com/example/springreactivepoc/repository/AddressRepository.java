package com.example.springreactivepoc.repository;

import com.example.springreactivepoc.entity.Address;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AddressRepository extends R2dbcRepository<Address, Long> {}