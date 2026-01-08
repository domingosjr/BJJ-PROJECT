package com.jiujitsu.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiujitsu.crud.exception.RecursoNaoEncontradoException;
import com.jiujitsu.crud.model.Aluno;
import com.jiujitsu.crud.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {
	
	private final AlunoRepository repository;
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	public Page<Aluno> listarTodos(String nome, Pageable pageable) {
		if (nome != null && !nome.isBlank()) {
			return repository.findByNomeContainingIgnoreCase(nome, pageable);
		}
		return repository.findAll(pageable);
	}

	public Aluno buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: id=" + id));
	}

	public Aluno criar(Aluno aluno) {
		aluno.setId(null); // garante que é criação
		return repository.save(aluno);
	}

	public Aluno atualizar(Long id, Aluno dados) {
		Aluno existente = buscarPorId(id);

		existente.setNome(dados.getNome());
		existente.setFaixa(dados.getFaixa());
		existente.setTelefone(dados.getTelefone());
		existente.setEmail(dados.getEmail());
		existente.setPlano(dados.getPlano());

		return repository.save(existente);
	}

	public void deletar(Long id) {
		Aluno existente = buscarPorId(id);
		repository.delete(existente);
	}
}
