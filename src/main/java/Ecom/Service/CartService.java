package Ecom.Service;


import Ecom.Exception.CartException;
import java.util.List;
import Ecom.Model.Product;
import Ecom.Model.Cart;


public interface CartService {
	
	public Cart addProductToCart(Integer userId, Integer productId) throws CartException;
	
	public Cart increaseProductQuantity(Integer cartId,Integer quantity) throws CartException;
	
	public Cart decreaseProductQuantity(Integer cartId,Integer quantity) throws CartException;
	
	public void removeProductFromCart(Integer cartId,Integer productId) throws CartException;
	
	public void removeAllProductFromCart(Integer cartId) throws CartException;
	
	public Cart getAllCartProduct(Integer cartId)throws CartException;
	
	

}
