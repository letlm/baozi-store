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

import com.store.baozi.model.Produto;
import com.store.baozi.repository.ProdutoRepository;

@RestController
@RequestMapping({ "/produto" })
public class ProdutoController {
	private ProdutoRepository repository;
	
	ProdutoController(ProdutoRepository produtoRepository) {
		this.repository = produtoRepository;
	}
	
	@PostMapping
	public ResponseEntity<Produto> create(@RequestBody Produto produto) {
		Produto novoProduto = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}
	
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Produto> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(produto -> ResponseEntity.ok().body(produto))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Produto>> findAll() {
		List<Produto> produtos = repository.findAll();
		return ResponseEntity.ok(produtos);
	}
	
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Object> delete(@PathVariable long id) {
		return repository.findById(id)
				.map(produto -> {
					repository.deleteById(id);
					return ResponseEntity.noContent().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Produto> update(@PathVariable("id") long id, @RequestBody Produto produto) {
		return repository.findById(id).map(record -> {
			record.setNome(produto.getNome());
			record.setPreco(produto.getPreco());
			record.setEstoque(produto.getEstoque());
			Produto updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		})
		.orElse(ResponseEntity.notFound().build());
	}
}
