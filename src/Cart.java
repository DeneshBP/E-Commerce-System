/*
* Name: Denesh Persaud
* Student #: 501090179
*/

/*
* This class represents an array of CartItems that can be added or removed
*/

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<CartItem>();
    }

    public void addItem(Product product, String productOptions) {
        CartItem cI = new CartItem(product, productOptions);
        cartItems.add(cI);
    }

    public void removeItem(Product prod) {
        for (CartItem cI : cartItems) {
            if (cI.getProduct().equals(prod)) {
                cartItems.remove(cI);
                break;
            }
        }
    }
    
    public ArrayList<CartItem> getItems(){
        return this.cartItems;
    }

    public void print(){
        for(CartItem cI : cartItems){
            cI.print();
        }
    }
}
