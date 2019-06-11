package ecommandes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommandes.data.ClientRepository;
import ecommandes.domain.Client;

/**
 * Implémentation de l'interface ClientService
 * @author landry
 *
 */
@Service
public class ClientServiceImpl implements ClientService {

	//déclaration du ClientRepository
	private ClientRepository clientRepository;
	
	@Autowired
	public ClientServiceImpl(final ClientRepository clientRepository) {
		// TODO Auto-generated constructor stub
		this.clientRepository = clientRepository;
	}
	
	@Override
	public Client save(Client client) {
		// TODO Auto-generated method stub
		return clientRepository.save(client);
	}

	@Override
	public Client findByCode(Integer code) {
		// TODO Auto-generated method stub
		return clientRepository.findByCode(code);
	}

	@Override
	public void delete(Integer code) {
		// TODO Auto-generated method stub
		clientRepository.deleteById(code);
	}

}
