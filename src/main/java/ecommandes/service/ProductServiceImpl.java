package ecommandes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommandes.data.ProductRepository;
import ecommandes.domain.Product;
import ecommandes.domain.ProductStock;

/**
 * Service d'impléméntation de l'interface ProductService
 * @author landry
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	//instanciation du productRepository
	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		// TODO Auto-generated constructor stub
		this.productRepository = productRepository;
	}
	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Integer code) {
		// TODO Auto-generated method stub

		productRepository.deleteById(code);
	}

	@Override
	public Product productStockRefueling(Integer productCode, ProductStock productStock){
		// TODO Auto-generated method stub

		//Recupération du produit
		Product product = productRepository.findById(productCode).get();
		
		//On doit s'assurer que la quantité du stock  à augmenter ne dépasse pas la quantité max du produit
		//si la quantité à aporter ne fait pas dépasser la quantité maximale autorisée
		if(effectiveStockquantity(product) + productStock.getQuantity() <= product.getMaxQuantity()) {
			
			//Ajout du stock à la liste des stocks
			product.getProductStockList().add(productStock);
			
			//persistance du produit
		return	productRepository.save(product);
		}else {
			return product;
		}
	}

	@Override
	public Product productDelivery(Integer productCode, Integer quantity){
		// TODO Auto-generated method stub

		//Recupération du produit
		Product product = productRepository.findById(productCode).get();
		
		//On se rassure d'abord que la quantité en stock est suffisante pour la livraison
		if(effectiveStockquantity(product) >= quantity) {
			
			//instanciation de la quantité restante
			int leftQuantity = quantity;
			
			//Itérateur sur la liste des stocks
			Iterator<ProductStock> iterator = product.getProductStockList().iterator();
			
			//Tantque qu'il ya des quantités à satisfaire
			while(leftQuantity > 0){
				
				//On prend le stock suivant
				ProductStock productStock = iterator.next();
				
				//Si la quantité du stock est plus grande que la quantité à satisfaire
				if((productStock.getQuantity() > leftQuantity)){
					
					//on diminue sipmlement le stock
					productStock.setQuantity(productStock.getQuantity() - leftQuantity);
					
					leftQuantity = 0;
				
					//Si la quantité du stock est plus petite que la quantité à stisfaire
				}else {
					
					//Mise à jour de la quantité restante
					leftQuantity = leftQuantity - productStock.getQuantity();
					
					//Supression du stock en question de la liste
					iterator.remove();
				}
				
			}
			
			//persistance du produit
			return productRepository.save(product);
			
		} else {
			
			return product;
		}
	}
	
	/**
	 * Cette fonction retourne la uantité effective du stock d'un produit
	 * @param product produit dont on veut la uantité en stock
	 * @return quantité du produit en stock
	 */
	private int effectiveStockquantity(Product product) {
		
		//Initialisation de quantity
		int quantity = 0;
		
		//parcours de la liste des stock d'un produit
		for (ProductStock productStock : product.getProductStockList()) {
			
			//sommation des quantités en stock
			quantity += productStock.getQuantity();
		}
		
		//Valeur de retour
		return quantity;
	}
	@Override
	public List<ProductDto> getProducts() {
		// TODO Auto-generated method stub
		
		//liste des produits
		List<Product> productList = productRepository.findAll();
		
		//Initialisation d'une liste de ProductDto
		List<ProductDto> productDtoList = new ArrayList();
		
		//On parcourt la liste
		for(Product product : productList) {
			
			//Variable contenant la quantité en stock
			int stockQuantity = 0;
			
			//Liste des productStock d'un product
			Set<ProductStock> productStockList = product.getProductStockList();
			
			//Parcourt de la liste des stocks
			for(ProductStock productStock: productStockList) {
				
				//On incrémente la quantité totale
				stockQuantity += productStock.getQuantity(); 
			}
			
			//On instancie un productDto
			ProductDto productDto= new ProductDto(product.getCode(), product.getLabel(),
					product.getPrice(), stockQuantity);
			
			//On ajoute le productDto à la liste
			productDtoList.add(productDto);
		}
		
		//On retourne la liste
		return productDtoList;
	}
	@Override
	public Product getProduct(Integer code) {
		// TODO Auto-generated method stub
		System.out.println("++++++++++++++++++++++++++On est avant oooooooooooooo++++++++++++++++");
		return productRepository.findById(code).get();

	}	
}
