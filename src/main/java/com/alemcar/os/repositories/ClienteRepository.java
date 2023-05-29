package com.alemcar.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alemcar.os.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
