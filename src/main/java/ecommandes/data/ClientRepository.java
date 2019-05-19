package ecommandes.data;

import org.springframework.data.repository.CrudRepository;

import ecommandes.domain.Client;

/**
 * Repository 
 * @author landry
 *
 */
public interface ClientRepository extends CrudRepository<Client, Integer> {

	Client findByCode(Integer code);
}
