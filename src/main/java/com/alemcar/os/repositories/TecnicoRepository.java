package com.alemcar.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alemcar.os.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}
