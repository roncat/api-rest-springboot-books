package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Book;
import com.algaworks.socialbooks.services.BooksService;

@RestController
@RequestMapping("/books")
public class BooksResources {

	@Autowired
	private BooksService booksService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(booksService.listar());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Book book) {
		book = booksService.salvar(book);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(book.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Book book = booksService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(book);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		booksService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Book book, 
			@PathVariable("id") Long id) {
		book.setId(id);
		booksService.atualizar(book);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long bookId, 
			@RequestBody Comentario comentario) {
		
		booksService.salvarComentario(bookId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listarComentarios(
			@PathVariable("id")Long bookId) {
		List<Comentario> comentarios = booksService.listarComentarios(bookId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}
}
