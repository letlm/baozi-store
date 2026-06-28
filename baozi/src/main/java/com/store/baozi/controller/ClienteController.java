package com.store.baozi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.baozi.model.Cliente;
import com.store.baozi.repository.ClienteRepository;

@RestController
@RequestMapping({ "/cliente" })
public class ClienteController {
	private ClienteRepository repository;
	
	ClienteController(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}
	
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		Cliente novoCliente = repository.save(cliente);
	    return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
	}
	
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Cliente> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(cliente -> ResponseEntity.ok().body(cliente))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> clientes = repository.findAll();
		return ResponseEntity.ok(clientes);
	}
	
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Object> delete(@PathVariable long id) {
		return repository.findById(id)
				.map(cliente -> {
					repository.deleteById(id);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		return repository.findById(id).map(record -> {
			record.setNome(cliente.getNome());
			record.setClienteDesde(cliente.getClienteDesde());
			Cliente updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		})
		.orElse(ResponseEntity.notFound().build());
	}
}
