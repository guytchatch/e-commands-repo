package ecommandes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;
import ecommandes.service.ProductDto;
import ecommandes.service.ProductService;

/**
 * Classe de test du controller ProductRestController
 * @author landry
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private ProductService productService;
	
	// These objecidts will be magically initialized by the initFields method below.
    private JacksonTester<Product> jsonProduct;
    private JacksonTester<ProductStock> jsonProductStock;
    private JacksonTester<List<ProductDto>> jsonProductDtoList;

    
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    
    @Test
    public void deliveryAndRefluelingProductStockTest() throws Exception {
       	    	
    	//Instanciatin d'un produit
		Product product = new Product("Drap", 20000, 200);
		product.setCode(1);

		//given
		//instanciation d'un ProductStock
		ProductStock productStock = new ProductStock(/*product,*/ 
				new LocalDate(2019,8,04),
				ProductStock.Quality.BONNE_QUALITE);
		productStock.setQuantity(20);
		productStock.setCode(1);
		productStock.setDeliveryDate(new LocalDate(2019,5,18));
		
		//Instanciatin d'un deuxime produit
		Product product2 = new Product("Drap", 20000, 200);
		product2.setCode(1);
		
		product.getProductStockList().add(productStock);
		
		given(productService.productStockRefueling(product.getCode(), productStock)).willReturn(product);
		
		//Déclaration d'un objet Response
		MockHttpServletResponse response;
			
		
		//Requête patch
		//Quand
		response = mvc.perform(patch("/e-commande/produit/1")
					.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
					.content(jsonProductStock.write(productStock).getJson()))
					.andDo(print())
					.andExpect(status().isOk()).andReturn().getResponse();
		
		System.out.println("-----------------" + product + "----------------------");

		System.out.println("+++++++++++++++" + response.getContentAsString() + "+++++++++++++++++");
	    System.out.println("*****************" + jsonProductStock.write(productStock).getJson() + "*****************");

		given(productService.productDelivery(1, 20)).willReturn(product2);

		
		//Alors
		//Le code de la réponse est 200
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		        
		//L'objet renvoyé est identique au paramètre
		//assertThat(response.getContentAsString()).isEqualTo(
		       // jsonProduct.write(product).getJson());
		//Requête patch
		//Quand
		response = mvc.perform(patch("/e-commande/produit/1/20").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					  .andDo(print())
					  .andReturn().getResponse();
		
		//Alors
		//Le code de la réponse est 200
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		//L'objet renvoyé est identique au paramètre
		assertThat(response.getContentAsString()).isEqualTo(
				            jsonProduct.write(product2).getJson());
    }
    
    @Test
    public void getAllProductStockTest() throws Exception {
    	//Instanciatin d'un produit
    	ProductDto productDto = new ProductDto(1,"Drap", 20000, 200);
        			
    	//Instanciatin d'un deuxime produit
    	ProductDto productDto2 = new ProductDto(2,"serviette", 2000, 00);
        			    		
    	//Instanciation d'une liste
    	List<ProductDto> productDtoList = new ArrayList<>();
    	
    	//Ajout d'un produit dans la liste
    	productDtoList.add(productDto);
    	
    	//ajout d'un deuxième produit
    	productDtoList.add(productDto2);
    	
    	//Etant donné
    	given(productService.getProducts()).willReturn(productDtoList);
    	
    	//Déclaration d'un objet Response
    	MockHttpServletResponse response;
    	
    	response = mvc.perform(get("/e-commande/produit")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();
	
    	//L'objet renvoyé est identique au paramètre
    	assertThat(response.getContentAsString()).isEqualTo(
    					            jsonProductDtoList.write(productDtoList).getJson());
    }
    
    @Test
    public void getProductTest()throws Exception{
    	
    	//Instanciatin d'un produit
    	Product product = new Product("Drap", 20000, 200);
    	product.setCode(1);
    	
    	//given
    	//instanciation d'un ProductStock
    	ProductStock productStock = new ProductStock(
    					new LocalDate(2019,8,04),
    					ProductStock.Quality.BONNE_QUALITE);
    	productStock.setQuantity(20);
    	productStock.setCode(1);
    	productStock.setDeliveryDate(new LocalDate(2019,5,18));
    	
		product.getProductStockList().add(productStock);

		//Déclaration d'un objet Response
		MockHttpServletResponse response;
			
		given(productService.getProduct(1)).willReturn(product);

				
		//Requête get
		//Quand
		response = mvc.perform(get("/e-commande/produit/1")
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
							.andDo(print())
							.andExpect(status().isOk()).andReturn().getResponse();
				
		System.out.println("0000000000000" + product + "00000000000000");

		System.out.println("+++++++++++++++" + response.getContentAsString() + "+++++++++++++++++");

				
		//Alors
		//Le code de la réponse est 200
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
				        
		//L'objet renvoyé est identique au paramètre
		assertThat(response.getContentAsString()).isEqualTo(
				        jsonProduct.write(product).getJson());
    }
}
