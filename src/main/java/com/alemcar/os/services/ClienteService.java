package com.alemcar.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alemcar.os.domain.Cliente;
import com.alemcar.os.domain.Pessoa;
import com.alemcar.os.dtos.ClienteDTO;
import com.alemcar.os.repositories.ClienteRepository;
import com.alemcar.os.repositories.PessoaRepository;
import com.alemcar.os.services.exceptions.DataIntegratyViolationException;
import com.alemcar.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	PessoaRepository pessoaRepository;

	/*
	 * Busca Cliente pelo ID
	 */
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	/*
	 * Busca todos os Cliente da base de dados
	 */
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/*
	 * Cria um Cliente
	 */
	public Cliente create(ClienteDTO objDto) {
		if (findByCPF(objDto) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Cliente(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone()));
	}

	/*
	 * Atualiza um Cliente
	 */
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return repository.save(oldObj);
	}

	/*
	 * Deleta um Cliente pelo ID
	 */
	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Pessoa possui Ordens de Serviço, não pode ser deletado!");
		}
		
		repository.deleteById(id);
	}
	
	/*
	 * Busca Cliente pelo CPF
	 */
	private Pessoa findByCPF(ClienteDTO objDto) {
		Pessoa obj = pessoaRepository.findByCPF(objDto.getCpf());
		
		if (obj != null) {
			return obj;
		}
		return null;
	}
}
