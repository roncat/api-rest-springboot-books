package com.algaworks.socialbooks.services.exceptions;

public class BookNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1869300553614629710L;

	public BookNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public BookNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}
