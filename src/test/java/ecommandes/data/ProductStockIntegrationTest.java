package ecommandes.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;

/**
 * Classe de test du produit et de son stock
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductStockIntegrationTest {

	//Injection du Product
	@Autowired
	private ProductRepository productRepository;
	
	//Injection du ProductStock
	@Autowired
	private ProductStockRepository productStockRepository;
	
	@Test
	public void createAndFindProductTest() {
		
		//given
		//Instanciation d'un produit
		Product product = new Product("Drap", 20000, 200);
		Product product2 = new Product("Serviettres", 1850, 250);

		
		//Enregistrement du produit
		//product = productRepository.save(product);
		//product2 = productRepository.save(product2);
		
		//then
		//assertThat(product.getCode().intValue()).isGreaterThanOrEqualTo(0);
		
		//Recherche du produit enregistré
		//Product productFound = productRepository.findById(product.getCode()).get();
				
		//Le client trouvé est bien celui enregistré
		//assertThat(productFound.getCode()).isEqualTo(product.getCode());
		
		//given
		//instanciation d'un ProductStock
		ProductStock productStock = new ProductStock(/*product,*/ new LocalDate(2019, 8, 4), ProductStock.Quality.BONNE_QUALITE);
		productStock.setQuantity(20);
		ProductStock productStock2 = new ProductStock(/*product,*/ new LocalDate(2020, 2, 4), ProductStock.Quality.QUALITE_MOYENNE);
		productStock2.setQuantity(75);
		ProductStock productStock3 = new ProductStock(/*product,*/ new LocalDate(2019, 6, 2), ProductStock.Quality.TRES_BONNE_QUALITE);
		productStock3.setQuantity(40);

		//Ajout du stock au produit
		product.getProductStockList().add(productStock);
		product.getProductStockList().add(productStock2);
		
		//Enregistrement du produit
		product = productRepository.save(product);

		//Enregistrement du ProductStock
		//productStock = productStockRepository.save(productStock);
		//productStock2 = productStockRepository.save(productStock2);
		//productStock3 = productStockRepository.save(productStock3);

		Product newproduct = productRepository.findById(product.getCode()).get();
			
		System.out.println("++++++++++++++++++++++++++++++++++++++++" + newproduct.getProductStockList() + "+++++++++++++++++++++++++++++++++++");
		//System.out.println("++++++++++++++++++++++++++++++++++++++++" + productStock + "+++++++++++++++++++++++++++++++++++");

		
		// Le code du produit doit être plus grand que 0
		assertThat(productStock.getCode()).isGreaterThanOrEqualTo(0);
		
		assertThat(productRepository.findById(product.getCode()).get().getProductStockList().size()).isEqualTo(2);
		
		product.getProductStockList().add(productStock3);
		
		product = productRepository.save(product);

		assertThat(productRepository.findById(product.getCode()).get().getProductStockList().size()).isEqualTo(3);

		System.out.println("++++++++++++++++++++++++++++++++++++++++" + newproduct.getProductStockList() + "+++++++++++++++++++++++++++++++++++");

		productRepository.deleteById(product.getCode());

		assertThat(productStockRepository.findAll().size()).isEqualTo(0);

	}
}
