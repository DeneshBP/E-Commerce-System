/*
* Name: Denesh Persaud
* Student #: 501090179
*/

/*
* This class contains a reference to a Product object and a
* reference to a productOptions string
*/

public class CartItem {
    private Product product;
    private String productOptions;

    public CartItem(Product product, String productOptions) {
        this.product = product;
        this.productOptions = productOptions;
    }

    public Product getProduct() {
        return product;
    }

    public String getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(String productOptions) {
        this.productOptions = productOptions;
    }

    public void print() {
        this.product.print();
        System.out.print(" Options: " + this.productOptions);
    }
}
