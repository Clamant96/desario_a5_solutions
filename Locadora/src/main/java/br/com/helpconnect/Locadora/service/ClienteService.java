package br.com.helpconnect.Locadora.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.helpconnect.Locadora.model.Cliente;
import br.com.helpconnect.Locadora.model.ClienteLogin;
import br.com.helpconnect.Locadora.model.Pedido;
import br.com.helpconnect.Locadora.repository.ClienteRepository;
import br.com.helpconnect.Locadora.repository.PedidoRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	/* CADASTRAR USUARIO NO SISTEMA */
	public Optional<Cliente> CadastrarCliente(Cliente cliente) {	

		if(clienteRepository.findByUsuario(cliente.getUsuario()).isPresent() && cliente.getId() == 0) {
			return null;
			
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(cliente.getSenha());
		cliente.setSenha(senhaEncoder);
		
		/*GERANDO CARRINHO USUARIO*/
		/* INSTANCIA UM NOVO CARRINHO 'Pedido' */
		Pedido pedido = new Pedido();
		
		/* REGISTRA O USUARIO NA BASE DE DADOS */
		clienteRepository.save(cliente);
		
		/* ASSOCIA O USUARIO AO CARRINHO */
		pedido.setCliente(cliente);
		
		/* REGISTRA O CARRINHO NA BASE DE DADOS */
		pedidoRepository.save(pedido);

		return Optional.of(clienteRepository.save(cliente));

	}
	
	/* LOGA USUARIO NO SISTEMA */
	public Optional<ClienteLogin> Logar(Optional<ClienteLogin> clienteLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Cliente> cliente = clienteRepository.findByUsuario(clienteLogin.get().getUsuario());
		
		/* CASO TENHA ALGUM VALOR DIGITADO, IREMOS COMPARAR OS DADOS QUE ESTAO CADASTRADOS NA BASE DE DADOS COM O QUE O USUARIO ACABOU DE DIGITAR */
		if (cliente.isPresent()) {
			/* COMPARA O QUE FOI DIGITADO NO BODY COM O QUE ESTA NO BANCO DE DADOS REFERENTE AQUELE DETERMINADO USUARIO */
			if (encoder.matches(clienteLogin.get().getSenha(), cliente.get().getSenha())) {
				
				/* CRIA UMA STRING COM O 'NOME_USUARIO:SENHA' */
				String auth = clienteLogin.get().getUsuario() + ":" + clienteLogin.get().getSenha();
				
				/* CRIA UM ARRAY DE BYTE, QUE RECEBE A STRING GERADA ACIMA E FORMATA NO PADRAO 'US-ASCII' */
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				
				/* GERA O TOKEN PARA ACESSO DE USUARIO POR MEIO DO ARRAY DE BY GERADO */
				String authHeader = "Basic " + new String(encodedAuth);
				
				/* INSERE O TOKEN GERADO DENTRO DE NOSSO ATRIBUTO TOKEN */
				clienteLogin.get().setToken(authHeader);				
				clienteLogin.get().setUsuario(cliente.get().getUsuario());
				clienteLogin.get().setSenha(cliente.get().getSenha());
				clienteLogin.get().setId(cliente.get().getId());
				clienteLogin.get().setPedidos(cliente.get().getPedidos());
				
				return clienteLogin;

			}
		}
		
		return null;
	}
	
	public Optional<Cliente> atualizarUsuario(Cliente usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return Optional.of(clienteRepository.save(usuario));
	}

}
