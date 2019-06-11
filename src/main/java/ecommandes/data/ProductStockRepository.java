package ecommandes.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ecommandes.domain.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Integer>{

	
}

