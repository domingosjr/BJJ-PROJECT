package com.jiujitsu.crud.repository;

import com.jiujitsu.crud.model.Aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	// Exemplo de filtro por nome (usaremos no GET com busca)
	List<Aluno> findByNomeContainingIgnoreCase(String nome);
	
	Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
