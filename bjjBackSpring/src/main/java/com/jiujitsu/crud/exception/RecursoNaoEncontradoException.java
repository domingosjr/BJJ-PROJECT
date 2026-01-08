package com.jiujitsu.crud.exception;

import jakarta.persistence.EntityNotFoundException;

public class RecursoNaoEncontradoException extends EntityNotFoundException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
