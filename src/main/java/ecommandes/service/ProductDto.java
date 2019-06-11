package ecommandes.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Cette classe représente le produit qui sera envoyé au client
 * @author landry
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ProductDto {

	//Code du produit
	private final int code;
	
	//Libelé du produit
	private final String label;

	//Prix du produit
	private final int price;

	//Quantité en stock
	private final int stockQuantity;

	/**
	 * Constructeur vide pour JSON
	 */
	ProductDto(){
		code = -1;
		label = "";
		price = -1;
		stockQuantity = -1;
	}
}
