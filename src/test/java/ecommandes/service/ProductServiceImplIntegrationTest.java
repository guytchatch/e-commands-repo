package ecommandes.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ecommandes.data.ProductRepository;
import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;

/**
 * Test du service de gestion des produits et de leurs stocks
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceImplIntegrationTest {

	/**
	 * Configuration de l'environnement des tests
	 * @author landry
	 *
	 */
	@TestConfiguration
	static class ClientServiceImplTestContextConfiguration{
		
		//Injection du repository de gestion des produits
		@Autowired
		private ProductRepository productRepository;
		
		//Instanciation du bean productService
		@Bean
		public ProductService productService() {
			return new ProductServiceImpl(productRepository);
		}
	}
	
	//Injection du service de gestion des produits
	@Autowired
	private ProductService productService;
	
	//Injection du repository de gestion des produits
	@Autowired
	private ProductRepository productRepository;
	
	@Before
	public void setUp(){
		
		//Instanciatin d'un produit
		Product product = new Product("Drap", 20000, 200);
		product.setCode(1);

		//given
		//instanciation d'un ProductStock
		ProductStock productStock = new ProductStock(/*product,*/ new LocalDate(2019, 8, 4), ProductStock.Quality.BONNE_QUALITE);
		productStock.setQuantity(20);
		ProductStock productStock2 = new ProductStock(/*product,*/ new LocalDate(2020, 2, 4), ProductStock.Quality.QUALITE_MOYENNE);
		productStock2.setQuantity(75);
		ProductStock productStock3 = new ProductStock(/*product,*/ new LocalDate(2019, 6, 2), ProductStock.Quality.TRES_BONNE_QUALITE);
		productStock3.setQuantity(40);
		
		//Ajout des stocks au produit
		product.getProductStockList().add(productStock);
		product.getProductStockList().add(productStock2);
		product.getProductStockList().add(productStock3);

		product = productRepository.save(product);
	}
	
	/**
	 * Test du service d'obtention d'un produit
	 */
	@Test
	public void getProductByCode() {
		
		Product product = productService.getProduct(productService.getProducts().get(0).getCode());
		
		System.out.println("tchatchhhhhhhhhhhhhhh" + product + "tchatchhhhhhhhhhh");
		
		assertThat(product.getCode()).isGreaterThan(0);
	}
	
	@Test
	public void refluyingAndDelivering() {

		//Instanciatin d'un produit
		Product product = productRepository.findById(1).get();
		
		//instanciation d'un ProductStock
		ProductStock productStock = new ProductStock(/*product, */new LocalDate(2024, 8, 4), ProductStock.Quality.BONNE_QUALITE);
		productStock.setQuantity(20);
		
		//Apel du service d'approvisionnement
		product = productService.productStockRefueling(product.getCode(), productStock);
		
		//Le productStock a été ajouté avec succès
		assertThat(product.getProductStockList().size()).isEqualTo(4);
		
		//instanciation d'un ProductStock
		ProductStock productStock2 = new ProductStock(/*product,*/ new LocalDate(2022, 10, 21), ProductStock.Quality.BONNE_QUALITE);
		productStock2.setQuantity(80);
				
		//Apel du service d'approvisionnement
		product = productService.productStockRefueling(product.getCode(), productStock2);
				
		//Le productStock n'a pas été ajputé car le stock max est dépassé
		assertThat(product.getProductStockList().size()).isEqualTo(4);
		
		//Appel du service de livraison
		product = productService.productDelivery(product.getCode(), 100);
		
		//Appel du service de livraison
		product = productService.productDelivery(product.getCode(), 35);
		
		//Appel du service de livraison
		product = productService.productDelivery(product.getCode(), 40);
		
		System.out.println("//////////////////////////////////////" + product.getProductStockList() +"////////////////////////////////////////////////");
	}
}
