package ecommandes.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import ecommandes.data.ClientRepository;
import ecommandes.domain.Client;

/**
 * Classe de test du ClientRepository
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryIntegrationTest {
	
	//Injection du ClientRepository
	@Autowired
	private ClientRepository clientRepository;
	
	/*
	 * Fonction de test d'enregistrement d'un client
	 */
	@Test
	public void createAndFindClientTest() {
		
		//given
		Client client = new Client("TCHATCHOUANG NONO Landry", 675066755, "guytchatch@yahoo.fr");
		
		//when
		//Sauvegarde du client landry
		Client clientSaved = clientRepository.save(client);
		
		//then
		assertThat(clientSaved.getCode().intValue()).isGreaterThanOrEqualTo(0);
		
		//Recherche du client enregistré
		Client clientFound = clientRepository.findByCode(clientSaved.getCode());
		
		//Le client trouvé est bien celui enregistré
		assertThat(clientFound.getCode()).isEqualTo(clientSaved.getCode());
	}
}
