package ecommandes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;
import ecommandes.service.ProductDto;
import ecommandes.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Cette classe implémente une API Rest de gestion des produits
 * @author landry
 *
 */
@Api(value="Système de gestion des produits", description="Opérations de gestion d'un produit dans l'application de commande")
@RestController
@RequestMapping("/e-commande/produit")
public class ProductRestController {

	//Délaration du Service de gestion des produits
	private ProductService productService;
	
	//Injection du composant productService dans le constructeur
	@Autowired
	public ProductRestController(final ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * API rest de création d'un produit
	 * @param product produit à enregistrer
	 * @return produit enregidtré
	 */
	@ApiOperation(value = "Enregistrer un nouveau produit", response = Product.class)
	@PostMapping
	public Product postProduct(@ApiParam(value = "Produit à créer", required = true) @RequestBody Product product) {
		
		//appel du service de cdréation d'un produit
		return productService.createProduct(product);
	}
	
	/**
	 * Point Rest d'obtention d'un produit
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "Rechercher un produit à partir de son code")
	@GetMapping(path = "/{code}")
	public Product getProduct(@ApiParam(value = "code du produit à rechercher", required = true) @PathVariable("code") int code) {
		
		//Appel du service 
		return productService.getProduct(code);
	}
	
	/**
	 * API rest de suppression d'un produit
	 * @param code code du produit à supprimer
	 */
	@ApiOperation(value = "Supprimer un produit")
	@DeleteMapping("/{code}")
	public void deleteProduct( @ApiParam(value = "code du produit à supprimer", required = true)@PathVariable("code") int code) {
	
		//Suppression du produit
		productService.deleteProduct(code);
	}
	
	/**
	 * API Rest de ravitaillement du stock d'un produit
	 * @param product produit à ravitailler
	 * @param productStock Stock à apporter
	 * @return produit mis à jour
	 */
	@ApiOperation(value = "Ravitailler le stock d'un produit", response = Product.class)
	@PatchMapping(
			path="/{code}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  Product productStockRefueling(
				@ApiParam(value = "code du produit à ravitailler", required = true) @PathVariable("code") int productCode,
				@ApiParam(value = "Objet productStock enregistré en BD", required = true) @RequestBody ProductStock productStock ) {
		
		//Appel du service de ravitaillement
		return productService.productStockRefueling(productCode, productStock);
	}
	
	/**
	 * API Rest de déstockage
	 * @param product produit à déstocker
	 * @param quantity quantité à déstocker
	 * @return produit mis à jour
	 */
	@ApiOperation(value = "diminuer le stock d'un produit", response = Product.class)
	@PatchMapping("/{code}/{quantity}")
	public Product productDelivery(
				@ApiParam(value = "code du produit à diminuer le stock", required = true) @PathVariable("code") int productCode, 
				@ApiParam(value = "Quantité à diminuer dans le stock", required = true) @PathVariable("quantity") int quantity){
		
		//Appel du service de livraison
		return productService.productDelivery(productCode, quantity);
	}
	
	/**
	 * Point Rest d'obtention de tous les produits de la BD
	 * @return
	 */
	@ApiOperation(value = "Rechercher tous les produits")
	@GetMapping()
	public List<ProductDto> getAllProducts(){
		
		//On retourne la liste en appelant le service d'obtention des produits
		return productService.getProducts();
	}
}
