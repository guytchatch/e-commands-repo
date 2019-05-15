package ecommandes.data;

import org.springframework.data.repository.CrudRepository;

import ecommandes.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

	Client findByCode(Integer code);
}
