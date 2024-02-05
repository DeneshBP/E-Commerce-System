/*
* Name: Denesh Persaud
* Student #: 501090179
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {
	private HashMap<String, Product> products = new HashMap<String, Product>();
	private ArrayList<Customer> customers = new ArrayList<Customer>();

	private ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
	private ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

	private HashMap<Product, Integer> productStats = new HashMap<Product, Integer>();

	// These variables are used to generate order numbers, customer id's, product
	// id's
	private int orderNumber = 500;
	private int customerId = 900;
	private int productId = 700;

	// Random number generator
	Random random = new Random();

	public ECommerceSystem() {
		products = getProducts("src/products.txt");

		// Create some customers. Notice how generateCustomerId() method is used
		customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
		customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
		customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
		customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));
	}

	private String generateOrderNumber() {
		return "" + orderNumber++;
	}

	private String generateCustomerId() {
		return "" + customerId++;
	}

	private String generateProductId() {
		return "" + productId++;
	}

	public void printAllProducts() {
		for (Product p : products.values()) {
			p.print();
		}
	}

	// Print all products that are books. See getCategory() method in class Product
	public void printAllBooks() {
		for (Product p : products.values()) {
			if (p.getCategory() == Product.Category.BOOKS) {
				p.print();
			}
		}
	}

	// Print all current orders
	public void printAllOrders() {
		for (ProductOrder po : orders) {
			po.print();
		}
	}

	// Print all shipped orders
	public void printAllShippedOrders() {
		for (ProductOrder po : shippedOrders) {
			po.print();
		}
	}

	// Print all customers
	public void printCustomers() {
		for (Customer c : customers) {
			c.print();
		}
	}

	/*
	 * Given a customer id, print all the current orders and shipped orders for them
	 * (if any)
	 */
	public void printOrderHistory(String customerId) {
		// Make sure customer exists - check using customerId
		// If customer does not exist, set errMsg String and return false
		// see video for an appropriate error message string
		// ... code here
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Print current orders of this customer
		System.out.println("Current Orders of Customer " + customerId);
		// enter code here
		for (ProductOrder po : orders) {
			if (po.getCustomer().equals(cust)) {
				po.print();
			}
		}

		// Print shipped orders of this customer
		System.out.println("\nShipped Orders of Customer " + customerId);
		// enter code here
		for (ProductOrder so : shippedOrders) {
			if (so.getCustomer().equals(cust)) {
				so.print();
			}
		}
	}

	public String orderProduct(String productId, String customerId, String productOptions) {
		// First check to see if customer object with customerId exists in array list
		// customers
		// if it does not, set errMsg and return null (see video for appropriate error
		// message string)
		// else get the Customer object
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Check to see if product object with productId exists in array list of
		// products
		// if it does not, set errMsg and return null (see video for appropriate error
		// message string)
		// else get the Product object
		Product prod = null;

		// check if prod in product map
		if (products.containsKey(productId)) {
			prod = products.get(productId);
		} else {
			throw new UnknownProductException("Product " + productId + " Not Found");
		}

		// Check if the options are valid for this product (e.g. Paperback or Hardcover
		// or EBook for Book product)
		// See class Product and class Book for the method vaidOptions()
		// If options are not valid, set errMsg string and return null;
		if (!(prod.validOptions(productOptions))) {
			throw new InvalidProductOptionsException("Product: " + prod.getName() + " ProductId: " + prod.getId()
					+ " Invalid Options: " + productOptions);
		}

		// Check if the product has stock available (i.e. not 0)
		// See class Product and class Book for the method getStockCount()
		// If no stock available, set errMsg string and return null
		if (prod.getStockCount(productOptions) <= 0) {
			throw new OutOfStockException("No stock available");
		}

		// Create a ProductOrder, (make use of generateOrderNumber() method above)
		// reduce stock count of product by 1 (see class Product and class Book)
		// Add to orders list and return order number string
		ProductOrder po = new ProductOrder(generateOrderNumber(), prod, cust, productOptions.toLowerCase());
		orders.add(po);
		prod.reduceStockCount(productOptions);
		productStats.put(prod, productStats.get(prod) + 1);

		return po.getOrderNumber();
	}

	/*
	 * Create a new Customer object and add it to the list of customers
	 */

	public void createCustomer(String name, String address) {
		// Check name parameter to make sure it is not null or ""
		// If it is not a valid name, set errMsg (see video) and return false
		// Repeat this check for address parameter
		if (name == null || name.equals("")) {
			throw new InvalidCustNameException("Invalid Customer Name");
		}
		if (address == null || address.equals("")) {
			throw new InvalidCustAddressException("Invalid Customer Address");
		}

		// Create a Customer object and add to array list
		Customer c = new Customer(generateCustomerId(), name, address);
		customers.add(c);
	}

	public ProductOrder shipOrder(String orderNumber) {
		// Check if order number exists first. If it doesn't, set errMsg to a message
		// (see video)
		// and return false
		// Retrieve the order from the orders array list, remove it, then add it to the
		// shippedOrders array list
		// return a reference to the order
		for (ProductOrder po : orders) {
			if (po.getOrderNumber().equals(orderNumber)) {
				shippedOrders.add(po);
				orders.remove(po);
				return po;
			}
		}
		throw new InvalidOrderNumException("Order " + orderNumber + " Not Found");
	}

	/*
	 * Cancel a specific order based on order number
	 */
	public void cancelOrder(String orderNumber) {
		// Check if order number exists first. If it doesn't, set errMsg to a message
		// (see video)
		// and return false
		for (ProductOrder po : orders) {
			if (po.getOrderNumber().equals(orderNumber)) {
				orders.remove(po);
			}
		}
		throw new InvalidOrderNumException("Order " + orderNumber + " Not Found");
	}

	// Sort products by increasing price
	public void sortByPrice() {
		// Get products array list
		ArrayList<Product> sortedProds = new ArrayList<Product>(products.values());

		// Sort the products by increasing price
		Collections.sort(sortedProds, new PriceComparator());

		// Print the products
		for (Product p : sortedProds) {
			p.print();
		}

	}

	private class PriceComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			if (a.getPrice() > b.getPrice()) return 1;
			if (a.getPrice() < b.getPrice()) return -1;	
			return 0;
		}
	}

	// Sort products alphabetically by product name
	public void sortByName()
	{
		// Get products array list
		ArrayList<Product> sortedProds = new ArrayList<Product>(products.values());

		// Sort the products by name
		Collections.sort(sortedProds, new NameComparator());

		// Print the products
		for (Product p : sortedProds) {
			p.print();
		}
	}

	private class NameComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			return a.getName().compareTo(b.getName());
		}
	}

	// Sort products alphabetically by product name
	public void sortCustomersByName()
	{
		Collections.sort(customers);
	}

	// Prints back all books with given author in increasing order of year published
	public void printBooksByAuthor(String author) {
		// Get products array list
		ArrayList<Book> sortedProds = new ArrayList<Book>();
		
		// Get the books
		for (Product p : products.values()) {
			if (p instanceof Book) {
				sortedProds.add((Book) p);
			}
		}

		// Sort the products by name
		Collections.sort(sortedProds, new YearComparator());

		// Print the products
		for (Product p : sortedProds) {
			if (((Book) p).getAuthor().equalsIgnoreCase(author)) {
				p.print();
			}
		}
	}

	private class YearComparator implements Comparator<Book>
	{
		public int compare(Book a, Book b)
		{
			if (a.getYear() > b.getYear()) return 1;
			if (a.getYear() < b.getYear()) return -1;	
			return 0;
		}
	}

	/*
	* Takes in prodcutId, customerId, and productOptions and adds the product to the cart
	*/
	public void addToCart(String productId, String customerId) {
		// Get customer
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Get product
		Product prod = null;
		if (products.containsKey(productId)) {
			prod = products.get(productId);
		} else {
			throw new UnknownProductException("Product " + productId + " Not Found");
		}

		// Get product options
		String productOptions = "";

		if (prod.getCategory() == Product.Category.BOOKS) {
			productOptions = getProductOptions();
		}

		// Check product options
		if (!(prod.validOptions(productOptions))) {
			throw new InvalidProductOptionsException("Invalid Product Options");
		}

		// Check if the product has stock available
		if (prod.getStockCount(productOptions) <= 0) {
			throw new OutOfStockException("Product " + prod.getName() + " is out of stock");
		}

		// Add the product to the cart
		cust.getCart().addItem(prod, productOptions);
	}

	/*
	* Takes in customerId and the index and removes the product from the cart
	*/
	public void removeFromCart(String productId, String customerId) {
		// Get customer
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Get product
		Product prod = null;
		if (products.containsKey(productId)) {
			prod = products.get(productId);
		} else {
			throw new UnknownProductException("Product " + productId + " Not Found");
		}

		// Remove the product from the cart
		cust.getCart().removeItem(prod);
	}

	/*
	* Takes in customerId and prints the cart
	*/
	public void printCart(String customerId) {
		// Get customer cart and print it
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Print the cart
		cust.getCart().print();
	}

	/*
	* Takes in customerId and orders all items in the cart
	*/
	public void orderCartItems(String customerId) {
		// Get customer cart and use for each loop to order each item
		Customer cust = null;
		for (Customer c : customers) {
			if (c.getId().equals(customerId)) {
				cust = c;
				break;
			}
		}
		if (cust == null) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		// Orders items in the cart and clear cart
		for (CartItem ci : cust.getCart().getItems()) {
			orderProduct(ci.getProduct().getId(), customerId, ci.getProductOptions());
		}
		cust.clearCart();
	}

	/*
	* Takes a file name, reads the file and adds all products to the products list
	*/
	private HashMap<String, Product> getProducts(String filename) {
		HashMap<String, Product> products = new HashMap<String, Product>();

		try {
			Scanner sc = new Scanner(new File(filename));

			Product.Category category = null; // First line
			String name = null; // Second line
			double price = 0; // Third line
			int stockCount = 0; // Fourth line
			String productInfo = null; // Fifth line
			String[] tempSCArray = null; // Used to split the fourth line
			String[] tempPIArray = null; // Used to split the fifth line
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine().replace("\n", "");
				
				// Get category
				if (line.equals("BOOKS")) {
					category = Product.Category.BOOKS;
				} else if (line.equals("CLOTHING")) {
					category = Product.Category.CLOTHING;
				} else if (line.equals("FURNITURE")) {
					category = Product.Category.FURNITURE;
				} else if (line.equals("COMPUTERS")) {
					category = Product.Category.COMPUTERS;
				} else {
					category = Product.Category.GENERAL;
				}
				
				// Get name
				line = sc.nextLine().replace("\n", "");
				name = line;
				
				// Get price
				line = sc.nextLine().replace("\n", "");
				price = Double.parseDouble(line);

				// Get stock count
				line = sc.nextLine().replace("\n", "");
				String tempSC = line;
					
				// Check category and apply correct stock count
				if (category == Product.Category.BOOKS) {
					tempSCArray = tempSC.split(" ");
				} else {
					stockCount = Integer.parseInt(tempSC);
				}
				
				// Get product options and add to product list
				line = sc.nextLine().replace("\n", "");
				productInfo = line;
					
				if (category == Product.Category.BOOKS){
					// Create new product
					tempPIArray = productInfo.split(":");
					Book b = new Book(name, generateProductId(), price, Integer.parseInt(tempSCArray[0]), Integer.parseInt(tempSCArray[1]), tempPIArray[0], tempPIArray[1], Integer.parseInt(tempPIArray[2]));
						
					// Add to product to products list and product stats map
					products.put(b.getId() ,b);
					productStats.put(b, 0);

				} else {
					// Create new product
					Product p = new Product(name, generateProductId(), price, stockCount, category);

					// Add to product to products list and product stats map
					products.put(p.getId(), p);
					productStats.put(p, 0);
				}
					
				// reset variables
				category = null;
				name = null;
				price = 0;
				stockCount = 0;
				productInfo = null;
				tempSCArray = null;
				tempPIArray = null;
			}

			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Products File not found");
			System.exit(1);
		}
		
		return products;

	}

	/*
	* Goes through the productStats map and prints the products in order of most to least sales
	*/
	public void printStats(){
		// Create a new list for sorted products
		ArrayList<Product> sortedProducts = new ArrayList<Product>(products.values());

		// Sort the list
		Collections.sort(sortedProducts, new StatComparator());

		// Print the product stats
		for (Product p : sortedProducts) {
			System.out.printf("Name: %-17s ID: %-5s #Sold: %-5d\n", p.getName(), p.getId(), productStats.get(p));
		}
	}

	private class StatComparator implements Comparator<Product> {
		public int compare(Product p1, Product p2) {
			return productStats.get(p2) - productStats.get(p1);
		}
	}

	/*
	* Gets the productOptions from user
	*/
	private String getProductOptions(){
		String productOptions = null;

		Scanner po = new Scanner(System.in);
		
		// Get product options
		System.out.print("\nBook Formats [Paperback | Hardcover | EBook]\nShoe Formats [Size (6-10) + Colour (Brown/Black)]\nEnter Product Options: ");
		productOptions = po.nextLine();

		return productOptions;
	}
}