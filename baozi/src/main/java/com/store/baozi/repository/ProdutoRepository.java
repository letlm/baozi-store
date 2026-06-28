package com.store.baozi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.baozi.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> { }
