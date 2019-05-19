package ecommandes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommandes.domain.Client;
import ecommandes.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


/**
 * Cette classe implémente une API Rest pour la gestion des clients
 * @author landry
 *
 */
@Api(value="Système de gestion des clients", description="Opération de gestion d'un client dans l'application de commande")
@RestController
@RequestMapping("/client")
public class ClientRestController {

	// déclaration du clientService
	private ClientService clientService;
	
	//Injection du clientService dans le clientRestController
	@Autowired
	public ClientRestController(final ClientService clientService) {
		this.clientService = clientService;
	}
	
	/**
	 * api Rest d'enregistrement d'un client
	 * @param client client à enregistrer
	 * @return client enregistré
	 */
	@ApiOperation(value = "Enregistrer un client", response = Client.class)
	@PostMapping
	public Client postClient( @ApiParam(value = "Objet Client enregistré en BD", required = true) @RequestBody Client client) {
		
		//sauvegarde et retourne le client sauvegardé
		return clientService.save(client);
	}
	
	/**
	 * Fonction permettant de retrouver un client par son code
	 * @param code code du client
	 * @return client trouvé
	 */
	@ApiOperation(value = "Recherche client", response = Client.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Recherche du client avec succes"),
		    @ApiResponse(code = 401, message = "Vous n'avez pas le droit de voir la ressource"),
		    @ApiResponse(code = 403, message = "Interdit d'accéder à la ressource"),
		    @ApiResponse(code = 404, message = "La Ressource que n'est pas trouvée")
		})
	@GetMapping("/{code}")
	public ResponseEntity<Client> getClient( @ApiParam(value = "code du client à rechercher", required = true) @PathVariable("code")int code) {
		
		//Recherche et renvoi du client
		return ResponseEntity.ok(clientService.findByCode(code));
	}
}
