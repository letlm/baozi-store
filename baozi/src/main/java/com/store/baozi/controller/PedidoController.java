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

import com.store.baozi.model.Pedido;
import com.store.baozi.repository.PedidoRepository;

@RestController
@RequestMapping({ "/pedido" })
public class PedidoController {
	private PedidoRepository repository;
	
	PedidoController(PedidoRepository pedidoRepository) {
		this.repository = pedidoRepository;
	}
	
	@PostMapping
	public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
		Pedido novoPedido = repository.save(pedido);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
	}
	
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Pedido> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(pedido -> ResponseEntity.ok().body(pedido))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Pedido>> findAll() {
		List<Pedido> pedidos = repository.findAll();
		return ResponseEntity.ok(pedidos);
	}
	
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Object> delete(@PathVariable long id) {
		return repository.findById(id)
				.map(pedido -> {
					repository.deleteById(id);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pedido> update(@PathVariable("id") long id, @RequestBody Pedido pedido) {
		return repository.findById(id).map(record -> {
			record.setClienteId(pedido.getClienteId());
			record.setProdutoId(pedido.getProdutoId());
			record.setQuantidade(pedido.getQuantidade());
			Pedido updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		})
		.orElse(ResponseEntity.notFound().build());
	}
}
