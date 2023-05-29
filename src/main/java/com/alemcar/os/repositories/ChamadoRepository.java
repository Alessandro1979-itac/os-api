package com.alemcar.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alemcar.os.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
