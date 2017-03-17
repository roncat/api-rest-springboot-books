package com.algaworks.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Book;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.BooksRepository;
import com.algaworks.socialbooks.services.exceptions.BookNaoEncontradoException;

@Service
public class BooksService {

	@Autowired
	private BooksRepository booksRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	public List<Book> listar() {
		return booksRepository.findAll();
	}
	
	public Book buscar(Long id) {
		Book book = booksRepository.findOne(id);
		
		if(book == null) {
			throw new BookNaoEncontradoException("O livro não pôde ser encontrado.");
		}
		
		return book;
	}
	
	public Book salvar(Book book) {
		book.setId(null);
		return booksRepository.save(book);
	}
	
	public void deletar(Long id) {
		try {
			booksRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new BookNaoEncontradoException("O livro não pôde ser encontrado.");
		}
	}
	
	public void atualizar(Book book) {
		verificarExistencia(book);
		booksRepository.save(book);
	}
	
	private void verificarExistencia(Book book) {
		buscar(book.getId());
	}
	
	public Comentario salvarComentario(Long bookId, Comentario comentario) {
		Book book = buscar(bookId);
		
		comentario.setBook(book);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long bookId) {
		Book book = buscar(bookId);
		
		return book.getComentarios();
	}
	
}
