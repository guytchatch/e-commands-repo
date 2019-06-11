package leadware.command.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Classe représentant une ligne de commande
 * @author landry
 *
 */
@Data
@RequiredArgsConstructor
public class OrderDetail implements Serializable {

	//code du détail de la commande
	private Integer code;
	
	//code du produit commandé
	private final Integer productCode;
	
	//quantité commandée
	private final Integer quantity;
	
	/**
	 * Constructeur sans paramètre pour JSON et JPA
	 */
	public OrderDetail() {
		
		this.productCode = -1;
		this.quantity = -1;
	}
}
