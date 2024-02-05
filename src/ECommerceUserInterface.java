/*
* Name: Denesh Persaud
* Student #: 501090179
*/

import java.util.Scanner;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface {
	public static void main(String[] args) {
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try {
				String action = scanner.nextLine();

				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS")) // List all products for sale
				{
					amazon.printAllProducts();
				} else if (action.equalsIgnoreCase("BOOKS")) // List all books for sale
				{
					amazon.printAllBooks();
				} else if (action.equalsIgnoreCase("CUSTS")) // List all registered customers
				{
					amazon.printCustomers();
				} else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();
				} else if (action.equalsIgnoreCase("SHIPPED")) // List all orders that have been shipped
				{
					amazon.printAllShippedOrders();
				} else if (action.equalsIgnoreCase("NEWCUST")) // Create a new registered customer
				{
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);
				} else if (action.equalsIgnoreCase("SHIP")) // ship an order to a customer
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					// Ship order to customer (see ECommerceSystem for the correct method to use
					ProductOrder order = amazon.shipOrder(orderNumber);
					order.print();
					
				} else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);

				} else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner
					productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					customerId = scanner.nextLine();

					// Order the product. Check for valid orderNumber string return and for error
					// message set in ECommerceSystem
					// Print Order Number string returned from method in ECommerceSystem
					String orderNumber = amazon.orderProduct(productId, customerId, "");
					System.out.println("Order #" + orderNumber);
				} else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format
																	// (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// Get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// Get book format and store in options string
					if (scanner.hasNextLine())
						options = scanner.nextLine();

					// Order product. Check for error mesage set in ECommerceSystem
					// Print order number string if order number is not null
					String orderNumber = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderNumber);

				} else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// Get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nSize [6, 7, 8, 9, 10]: ");
					// Get shoe size and store in options
					if (scanner.hasNextLine())
						options = scanner.nextLine();

					System.out.print("\nColor [Black, Brown]: ");
					// Get shoe color and append to options
					if (scanner.hasNextLine())
						options += " " + scanner.nextLine().toLowerCase();

					// Order shoes
					String orderNumber = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderNumber);

				} else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					// Cancel order. Check for error
					amazon.cancelOrder(orderNumber);

				} else if (action.equalsIgnoreCase("ADDTOCART")) {
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner
					productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					customerId = scanner.nextLine();

					// Add product to cart
					amazon.addToCart(productId, customerId);

				} else if (action.equalsIgnoreCase("REMCARTITEM")) {
					String customerId = "";
					String productId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner
					productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					customerId = scanner.nextLine();

					// Remove item from cart
					amazon.removeFromCart(productId, customerId);

				} else if (action.equalsIgnoreCase("PRINTCART")) {
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					customerId = scanner.nextLine();

					// Print cart
					amazon.printCart(customerId);

				} else if (action.equalsIgnoreCase("ORDERITEMS")) {
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					customerId = scanner.nextLine();

					// Order cart items
					amazon.orderCartItems(customerId);

				} else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.sortByPrice();

				} else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.sortByName();

				} else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();

				} else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) // prints all books by the given author
				{
					String author = "";

					System.out.print("Enter Author Name: ");
					// Gets author name from scanner
					if (scanner.hasNextLine())
						author = scanner.nextLine();

					amazon.printBooksByAuthor(author);

				} 
				else if (action.equalsIgnoreCase("STATS"))
				{
					// Prints statistics of products
					amazon.printStats();
				}
				else if (action.equalsIgnoreCase("HELP")) // prints all commands
				{
					System.out.println("\nCOMMANDS:");
					System.out.println("-------------------------------------------------------");
					System.out.println("PRODS - Prints all products");
					System.out.println("BOOKS - Prints all books");
					System.out.println("CUSTS - Prints all customers");
					System.out.println("ORDERS - Prints all orders");
					System.out.println("SHIPPED - Prints all shipped orders");
					System.out.println("CUSTORDERS - Prints all orders for a customer");
					System.out.println("NEWCUST - Creates a new customer");
					System.out.println("ORDER - Orders a product");
					System.out.println("ORDERBOOK - Orders a book");
					System.out.println("ORDERSHOES - Orders shoes");
					System.out.println("SHIP - Ships an order");
					System.out.println("ADDTOCART - Adds a product to the customer's cart");
					System.out.println("REMCARTITEM - Removes a product from the customer's cart");
					System.out.println("PRINTCART - Prints all the products in the customer's cart");
					System.out.println("ORDERITEMS - Orders all items in the customer's cart");
					System.out.println("CANCEL - Cancels an order");
					System.out.println("PRINTBYPRICE - Sorts products by price");
					System.out.println("PRINTBYNAME - Sorts products by name");
					System.out.println("SORTCUSTS - Sorts customers by name");
					System.out.println("BOOKSBYAUTHOR - Prints all books by the given author");
					System.out.println("STATS - Prints the stats of all products");
					System.out.println("QUIT - Exits the program");
				}
				
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			System.out.print("\n>");
		}

		scanner.close();
	}
}
