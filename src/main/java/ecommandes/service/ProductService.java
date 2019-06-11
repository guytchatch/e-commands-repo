package ecommandes.service;

import java.util.List;
import java.util.Set;

import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;

/**
 * Interface service des opérations sur un produit
 * @author landry
 *
 */
public interface ProductService {

	/**
	 * Création d'un produit
	 * @param product produit à créer
	 * @return produit créé (avec code produit)
	 */
	public Product createProduct(Product product);
	
	/**
	 * suppression d'un produit
	 * @param code code du produit à supprimer
	 */
	public void deleteProduct(Integer code);
	
	/**
	 * ravitailleemnt d'un produit
	 * @param product  code du produit à ravitailler
	 * @param productStock stock à apporter
	 * @return 
	 * @throws Exception quantité maximale dépassée
	 */
	public Product productStockRefueling(Integer productCode, ProductStock productStock);
	
	/**
	 * Sortie d'une quantité dans le stock d'un produit
	 * @param product code du produit dont on veut diminuer le stock
	 * @param quantity quantiter à retrancher dans le stock
	 * @throws Exception Stock insuffisant
	 */
	public Product productDelivery(Integer productCode, Integer quantity);
	
	/**
	 * Liste de tous les produits
	 * @return
	 */
	public List<ProductDto> getProducts();
	
	/**
	 * Obtenir un produit à partir de son identifiant
	 * @param code code du produit
	 * @return produit
	 */
	public Product getProduct(Integer code);

}
