package com.alemcar.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alemcar.os.domain.Chamado;
import com.alemcar.os.domain.Cliente;
import com.alemcar.os.domain.Tecnico;
import com.alemcar.os.domain.enuns.Perfil;
import com.alemcar.os.domain.enuns.Prioridade;
import com.alemcar.os.domain.enuns.Status;
import com.alemcar.os.repositories.ChamadoRepository;
import com.alemcar.os.repositories.ClienteRepository;
import com.alemcar.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Luiz Felipe", "11765549990", "email@gmail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Gabriel", "69315400925", "email1@gmail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.TECNICO);
		Tecnico tec3 = new Tecnico(null, "Vitor", "33296342940", "email2@gmail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.TECNICO);
		Tecnico tec4 = new Tecnico(null, "Valdir", "64471145797", "email3@gmail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.TECNICO);

		Cliente cli1 = new Cliente(null, "Linus Torvalds", "41308163142", "torvalds@email.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Max", "41931024162", "max@email.com", encoder.encode("123"));

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
}
