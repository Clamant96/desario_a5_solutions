package br.com.helpconnect.Locadora.seguranca;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.helpconnect.Locadora.model.Cliente;
import br.com.helpconnect.Locadora.repository.ClienteRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Cliente> cliente = clienteRepository.findByUsuario(username);
		
		cliente.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		
		return cliente.map(UserDetailsImplementation::new).get();
	}
	
}
