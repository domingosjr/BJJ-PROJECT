package com.jiujitsu.crud.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiujitsu.crud.model.Aluno;
import com.jiujitsu.crud.service.AlunoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // porta padrão do Vite
public class AlunoController {
	
	private final  AlunoService alunoService;

	// GET /api/alunos?nome=dom&page=0&size=10&sort=nome,asc
	@GetMapping
	public ResponseEntity<Page<Aluno>> listar(@RequestParam(required = false) String nome,
			@PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {

		return ResponseEntity.ok(alunoService.listarTodos(nome, pageable));
	}

	// GET /api/alunos/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(alunoService.buscarPorId(id));
	}

	// POST /api/alunos
	@PostMapping
	public ResponseEntity<Aluno> criar(@Valid @RequestBody Aluno aluno) {
		Aluno criado = alunoService.criar(aluno);
		return ResponseEntity.created(URI.create("/api/alunos/" + criado.getId())).body(criado);
	}

	// PUT /api/alunos/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {
		return ResponseEntity.ok(alunoService.atualizar(id, aluno));
	}

	// DELETE /api/alunos/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		alunoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
