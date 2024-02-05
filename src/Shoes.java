/*
* Name: Denesh Persaud
* Student #: 501090179
*/

import java.util.HashMap;

public class Shoes extends Product{

    // Declare variables for stock
    private HashMap<String, Integer> stock = new HashMap<>();
    
    // Constructor
    public Shoes(String name, String id, double price, HashMap<String, Integer> stock) {
        super(name, id, price, 100000, Product.Category.CLOTHING);
        this.stock = stock;
    }

    // Check if given product options are valid
    public boolean validOptions(String productOptions) {
        // Check if productOptions is one of the keys in the hashmap
        return (stock.containsKey(productOptions));
    }

    // Override getStockCount()
    public int getStockCount(String productOptions) {
        // Get stock count of productOptions in hashmap
        return stock.get(productOptions);
    }

    // Override setStockCount()
    public void setStockCount(int stockCount, String productOptions) {
        // Set stock count of productOptions in hashmap
        stock.put(productOptions, stockCount);
    }

    // Override removeStockCount()
    public void reduceStockCount(String productOptions) {
        // Reduce stock count of productOptions in hashmap
        stock.put(productOptions, stock.get(productOptions) - 1);
    }
}
