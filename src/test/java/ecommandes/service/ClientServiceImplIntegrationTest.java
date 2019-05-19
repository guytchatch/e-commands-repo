package ecommandes.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ecommandes.data.ClientRepository;
import ecommandes.domain.Client;

/**
 * Test du service de gestion des clients
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
public class ClientServiceImplIntegrationTest {
	
	@TestConfiguration
	static class ClientServiceImplTestContextConfiguration{
		
		@Autowired
		private ClientRepository clientRepository;
		
		@Bean
		public ClientService clientService() {
			
			return new ClientServiceImpl(clientRepository);
		}
	}
	
	//Injection du composant clientService
	@Autowired
	ClientService clientService;
	
	//mock du ClientRepository
	@MockBean
	private ClientRepository clientRepository;

	//A faire avant les tests
	@Before
	public void setUp() {
		
		//Instanciation d'un client
		Client client = new Client("TCHATCHOUANG NONO Landry", 675066755, "guytchatch@yahoo.fr");
		
		//Ajout d'un code
		client.setCode(1);

		//on renvoie client lorsque le code est 1
		Mockito.when(clientRepository.findByCode(1)).thenReturn(client);
	}
	
	@Test
	public void findClientTest() {
		
		//Recherche du client de code 1
		Client clientFound = clientService.findByCode(1);
		
		//le code du client doit être 1
		assertThat(clientFound.getCode()).isEqualTo(1);
	}
}
