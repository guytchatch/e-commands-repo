package ecommandes.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ecommandes.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	public Product findByCode(Integer code);
	
}
