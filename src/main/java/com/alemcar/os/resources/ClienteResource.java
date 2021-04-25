package com.alemcar.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alemcar.os.domain.Cliente;
import com.alemcar.os.dtos.ClienteDTO;
import com.alemcar.os.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	/*
	 * Busca pelo ID
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO objDTO = new ClienteDTO(service.findById(id));

		return ResponseEntity.ok().body(objDTO);
	}
	
	/*
	 * Lista todos objetos do tipo Cliente na base
	 */
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listDTO = service.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	/*
	 * Cria um novo Cliente
	 */
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDto) {
		Cliente newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	/*
	 * Atualiza um Cliente
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto) {
		ClienteDTO newObj = new ClienteDTO(service.update(id, objDto));

		return ResponseEntity.ok().body(newObj);
	}

	/*
	 * Delete Cliente
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
