package ecommandes.service;

import ecommandes.domain.Client;

/**
 * Interface ClientService
 * @author landry
 *
 */
public interface ClientService {

	/**
	 * fonction de sauvegarde d'un client
	 * @param client : client à sauvegarder
	 * @return : Client sauvegardé
	 */
	public Client save(Client client);
	
	/**
	 * Fonction de recherche d'un client par son code
	 * @param code : code du client recherché
	 * @return : client trouvé
	 */
	public Client findByCode(Integer code);
	
	/**
	 * Fonction de suppression d'un client
	 * @param code code du client à supprimer
	 */
	public void delete(Integer code);

}
