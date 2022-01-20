package br.com.helpconnect.Locadora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helpconnect.Locadora.model.Filmes;

@Repository
public interface FilmesRepository extends JpaRepository<Filmes, Long>{

	public Optional<Filmes> findByTituloIgnoreCase(String titulo);
	
}
