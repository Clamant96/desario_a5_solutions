package br.com.helpconnect.Locadora.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "produto")
public class Filmes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long filmesId;
	
	@NotNull
	@Size(max = 50)
	private String titulo;
	
	@NotNull
	@Size(max = 255)
	private String diretor;
	
	@NotNull
	private int estoque;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	  name = "produto_pedido",
	  joinColumns = @JoinColumn(name = "produto_id"),
	  inverseJoinColumns = @JoinColumn(name = "pedido_id")
	)
	@JsonIgnoreProperties({"data", "produtos", "cliente"})
	private List<Pedido> pedidos = new ArrayList<>();
	
	public long getFilmesId() {
		return filmesId;
	}

	public void setFilmesId(long filmesId) {
		this.filmesId = filmesId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}
