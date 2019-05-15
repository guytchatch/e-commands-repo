package ecommandes.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommandes.domain.Client;
import ecommandes.service.ClientService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Classe de test du controller ClientController
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ClientRestController.class)
public class ClientRestControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private ClientService clientService;
	
	 // These objecidts will be magically initialized by the initFields method below.
    private JacksonTester<Client> jsonClient;
	
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    
	@Test
	public void createAndFindClient()  throws Exception{
		
		//Déclaration d'un client
		Client client = new Client("TCHATCHOUANG NONO Landry", 675066755, "guytchatch@yahoo.fr");
		client.setCode(1);

		//
		given(clientService.save(client)).willReturn(client);
		given(clientService.findByCode(1)).willReturn(client);
		
		//Déclaration d'un objet Response
		MockHttpServletResponse response;
	
		//Requête post
		//Quand
		response = mvc.perform(post("/client").contentType(MediaType.APPLICATION_JSON)
					.content(jsonClient.write(client).getJson()))
					.andReturn().getResponse();
		//Alors
		//Le code de la réponse est 200
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        //L'objet renvoyé est identique au paramètre
        assertThat(response.getContentAsString()).isEqualTo(
                jsonClient.write(client).getJson());
        
		//Requête get
		response = mvc.perform(get("/client/1")).andReturn().getResponse();
		
		System.out.println("+++++++++++++++" + response.getContentAsString() + "+++++++++++++++++");
	    System.out.println("+++++++++++++++" + jsonClient.write(client).getJson() + "+++++++++++++++++");
		
		//Le code de la réponse est 200
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        //L'objet renvoyé est identique au paramètre
        assertThat(response.getContentAsString()).isEqualTo(
                jsonClient.write(client).getJson());
	}
}
