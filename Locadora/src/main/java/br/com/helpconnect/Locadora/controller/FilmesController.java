package br.com.helpconnect.Locadora.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.helpconnect.Locadora.model.Cliente;
import br.com.helpconnect.Locadora.model.Filmes;
import br.com.helpconnect.Locadora.repository.ClienteRepository;
import br.com.helpconnect.Locadora.repository.FilmesRepository;
import br.com.helpconnect.Locadora.service.FilmesService;

import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/locadora")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FilmesController {
	
	@Autowired
	private FilmesRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FilmesService service;
	
	@GetMapping
	public ResponseEntity<List<Filmes>> findAllByProdutos() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	/*@GetMapping("/{id}")
	public ResponseEntity<Filmes> findByIdProduto(@PathVariable long id) {
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}*/
	
	/*@GetMapping("/{nome}")
	public ResponseEntity<List<Filmes>> findAllByNomeProdutos(@PathVariable String nome) {
		
		return ResponseEntity.ok(repository.findAllBytituloContainingIgnoreCase(nome));
	}*/
	
	@PostMapping
	public ResponseEntity<Filmes> postProduto(@RequestBody Filmes produto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Filmes> putProduto(@RequestBody Filmes produto) {
		
		return ResponseEntity.ok(repository.save(produto));
	}

	/*@PutMapping("/produto_pedido/produtos/{idProduto}/pedidos/{idPedido}")
	public ResponseEntity<Filmes> putProduto(@PathVariable long idProduto, @PathVariable long idPedido) {
		
		return ResponseEntity.ok(service.compraProduto(idProduto, idPedido));
	}*/
	
	@GetMapping("/produto_pedido/{token}/{titulo}")
	public ResponseEntity<Filmes> alugarTitulo(@PathVariable String token, @PathVariable String titulo) {
		token = token.replace("Basic", "");
		
		byte[] decodedBytes = Base64.decodeBase64(token);
		String decodedMime = new String(decodedBytes);
		
		//System.out.println("Usuario: "+ decodedMime);
		
		Optional<Cliente> cliente = clienteRepository.findByUsuario(decodedMime.split(":")[0]);
		Optional<Filmes> produto = repository.findByTituloIgnoreCase(titulo);
		
		/*System.out.println("ID Cliente: "+ cliente.get().getId());
		System.out.println("Usuario: "+ cliente.get().getUsuario());
		
		System.out.println("ID Produto: "+ produto.get().getFilmesId());
		System.out.println("Titulo: "+ produto.get().getTitulo());*/

		return ResponseEntity.ok(service.compraProduto(produto.get().getFilmesId(), cliente.get().getId()));
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable long id) {
		
		repository.deleteById(id);
	}

}
