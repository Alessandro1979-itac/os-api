package com.alemcar.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alemcar.os.domain.Cliente;
import com.alemcar.os.domain.OS;
import com.alemcar.os.domain.Tecnico;
import com.alemcar.os.domain.enuns.Prioridade;
import com.alemcar.os.domain.enuns.Status;
import com.alemcar.os.dtos.OSDTO;
import com.alemcar.os.repositories.OSRepository;
import com.alemcar.os.services.exceptions.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository repository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	/*
	 * Busca OS pelo ID
	 */
	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
	}

	/*
	 * Busca todos as OSs da base de dados
	 */
	public List<OS> findAll() {
		return repository.findAll();
	}

	/*
	 * Cria uma OS
	 */
	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}

	/*
	 * Atualiza uma OS
	 */
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());

		return fromDTO(obj);
	}

	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));

		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());

		newObj.setTecnico(tec);
		newObj.setCliente(cli);

		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}

		return repository.save(newObj);
	}
}
