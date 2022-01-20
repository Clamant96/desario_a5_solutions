package br.com.helpconnect.Locadora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.helpconnect.Locadora.model.Pedido;
import br.com.helpconnect.Locadora.model.Filmes;
import br.com.helpconnect.Locadora.repository.PedidoRepository;
import br.com.helpconnect.Locadora.repository.FilmesRepository;

@Service
public class FilmesService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private FilmesRepository produtoRepository;
	
	double a = 0;
	int posicao = 0; // aramazena a posicao do item dentro do array de lista de desejos
	
	public Filmes compraProduto(long idProduto, long idPedido) {
			
		Optional<Filmes> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Pedido> pedidoExistente = pedidoRepository.findById(idPedido);
		
		if(produtoExistente.isPresent() && pedidoExistente.isPresent() && produtoExistente.get().getEstoque() == 0) {
			/* ACRESCENTA MAIS 50 PRODUTOS AO ESTOQUE */
			//produtoExistente.get().setEstoque(5);
			return null;
			
		}
		
		for(int i = 0; i < pedidoExistente.get().getProdutos().size(); i++) {
			if(pedidoExistente.get().getProdutos().get(i).getFilmesId() == produtoExistente.get().getFilmesId()) {
				deletarProduto(produtoExistente.get().getFilmesId(), pedidoExistente.get().getId());
				
			}
		}
		
		/* SE O 'PRODUTO' E 'PEDIDO' EXISTIREM, E SE O 'ESTOQUE' CONTEM PRODUTOS DISPONIVEIS ENTRA NA CONDICAO */
		if(produtoExistente.isPresent() && pedidoExistente.isPresent() && produtoExistente.get().getEstoque() >= 0 && !(pedidoExistente.get().getProdutos().isEmpty())) {
			
			/* ADICIONA O PRODUTO AO CARRINHO DO USUARIO */
			produtoExistente.get().getPedidos().add(pedidoExistente.get());
			
			System.out.println("Retorno: "+ pedidoExistente.get().getProdutos().contains(produtoExistente.get()));
			
			System.out.println("QTD produtos "+ pedidoExistente.get().getProdutos().size());
			
			/* ARMAZENA A QTD DE PRODUTOS */
			int contador = 0;
			
			/* ARMAZENA OS IDs DOS PRODUTOS LISTADOS DENTRO DO CARRINHO DO USUARIO */
			long[] vetor = new long[pedidoExistente.get().getProdutos().size()];
			
			for(int i = 0; i < pedidoExistente.get().getProdutos().size(); i++) {
				
				vetor[i] = pedidoExistente.get().getProdutos().get(i).getFilmesId();
				
				System.out.println("Posicao do vetor ["+ i +"] = "+ vetor[i]);
				System.out.println("Produto ID: "+ produtoExistente.get().getFilmesId());
				
				if(vetor[i] == produtoExistente.get().getFilmesId()) {
					contador++;
					
				}
				
			}
			
			/* COMPENSA ACRESCENTANDO O NOVO PRODUTO AO CARRINHO ==> O ID INFORMADO */
			contador++;
			System.out.println("QTD de produto: "+ contador);
			
			/* DEBITA O ESTOQUE SEMPRE QUE E INSERIDO UM PRODUTO A UM CARRINHO */
			produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() - 1);
			
			produtoRepository.save(produtoExistente.get());
			pedidoRepository.save(pedidoExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
			
		}else if(produtoExistente.isPresent() && pedidoExistente.isPresent()) {
			/* ADICIONA O PRODUTO AO CARRINHO DO USUARIO */
			produtoExistente.get().getPedidos().add(pedidoExistente.get());
			
			/* GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE */
			produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() - 1);
			
			produtoRepository.save(produtoExistente.get());
			pedidoRepository.save(pedidoExistente.get());
			
			return produtoRepository.save(produtoExistente.get());
			
		}
		
		return null;
	}
	
	/* DELETAR OBJETOS DO PRODUTO */
	public void deletarProduto(long idProduto, long idPedido) {

		Optional<Filmes> produtoExistente = produtoRepository.findById(idProduto);
		Optional<Pedido> pedidoExistente = pedidoRepository.findById(idPedido);
		
		if(pedidoExistente.get().getProdutos().contains(produtoExistente.get())) {
			/* REMOVE O CARRINHO DO PRODUTO */
			produtoExistente.get().getPedidos().remove(pedidoExistente.get());
			
			/* GERENCIA O ESTOQUE DEBITNADO UM PRODUTO DO ESTOQUE */
			produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() + 1);
			
			int contador = 0;
			
			/* ARMAZENA OS IDs DOS PRODUTOS LISTADOS DENTRO DO CARRINHO DO USUARIO */
			long[] vetor = new long[pedidoExistente.get().getProdutos().size()];
			
			for(int i = 0; i < pedidoExistente.get().getProdutos().size(); i++) {
				
				vetor[i] = pedidoExistente.get().getProdutos().get(i).getFilmesId();
				
				System.out.println("Posicao do vetor ["+ i +"] = "+ vetor[i]);
				System.out.println("Produto ID: "+ produtoExistente.get().getFilmesId());
				
				if(vetor[i] == produtoExistente.get().getFilmesId()) {
					contador++;
					
				}
				
			}
			
			produtoRepository.save(produtoExistente.get());
			pedidoRepository.save(pedidoExistente.get());
			
		}
		
	}
	
	/* PESQUISANDO POR PRODUTO ESPECIFICO EM UMA LISTA DE DESEJOS DE PRODUTOS */
	public List<Filmes> pesquisaPorProdutoNoCarrinho(long idPedido) {
		Optional<Pedido> pedidoExistente = pedidoRepository.findById(idPedido);
		
		if(pedidoExistente.isPresent()) {
			pedidoExistente.get().getProdutos();
			
			return pedidoRepository.save(pedidoExistente.get()).getProdutos();
			
		}
		
		return null;
		
	}

}
