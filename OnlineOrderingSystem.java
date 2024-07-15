package onlineoderingsystem;



import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private int productId;
    private String name;
    private double price;

    public Product(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product(id=" + productId + ", name=" + name + ", price=" + price + ")";
    }
}

class Customer {
    private static int nextId = 1; // Static counter for unique ID generation
    private int customerId;
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.customerId = nextId++;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer(id=" + customerId + ", name=" + name + ", email=" + email + ")";
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem(product=" + product.getName() + ", quantity=" + quantity + ", total_price=" + getTotalPrice() + ")";
    }
}

class Order {
    private static int nextId = 1; // Static counter for unique ID generation
    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> items;

    public Order(Customer customer) {
        this.orderId = nextId++;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    public double getTotalOrderPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder itemStr = new StringBuilder();
        for (OrderItem item : items) {
            itemStr.append(item).append("\n");
        }
        return "Order(id=" + orderId + ", customer=" + customer.getName() + ",\nItems:\n" + itemStr + "Total price: " + getTotalOrderPrice() + ")";
    }
}

class Admin {
    public void manageProducts(Scanner scanner, ArrayList<Product> products) {
        while (true) {
            System.out.println("\nAdmin - Manage Products");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter product price: ");
                double productPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                products.add(new Product(productId, productName, productPrice));
                System.out.println("Product added successfully!");
            } else if (choice == 2) {
                System.out.println("\nList of Products:");
                for (Product product : products) {
                    System.out.println(product);
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void manageOrders(Scanner scanner, ArrayList<Order> orders) {
        System.out.println("\nList of Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public void manageCustomers(Scanner scanner, ArrayList<Customer> customers) {
        System.out.println("\nList of Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}

class PaymentGateway {
    public void processPayment() {
        System.out.println("Processing payment...");
    }
}

public class OnlineOrderingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        createProducts(products);

        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                Admin admin = new Admin();
                while (true) {
                    System.out.println("\nAdmin Menu");
                    System.out.println("1. Manage Products");
                    System.out.println("2. Manage Orders");
                    System.out.println("3. Manage Customers");
                    System.out.println("4. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int adminChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (adminChoice == 1) {
                        admin.manageProducts(scanner, products);
                    } else if (adminChoice == 2) {
                        admin.manageOrders(scanner, orders);
                    } else if (adminChoice == 3) {
                        admin.manageCustomers(scanner, customers);
                    } else if (adminChoice == 4) {
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (choice == 2) {
                Customer customer = createCustomer(scanner, customers);
                Order order = createOrder(scanner, customer, products);
                orders.add(order);
                displayOrderDetails(order);

                PaymentGateway paymentGateway = new PaymentGateway();
                paymentGateway.processPayment();
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void createProducts(ArrayList<Product> products) {
        // Adding predefined products to the list
        products.add(new Product(1, "Laptop", 1000.00));
        products.add(new Product(2, "Smartphone", 700.00));
        products.add(new Product(3, "Headphones", 150.00));
        products.add(new Product(4, "Monitor", 300.00));
        products.add(new Product(5, "Keyboard", 50.00));
    }

    private static Customer createCustomer(Scanner scanner, ArrayList<Customer> customers) {
        System.out.println("--------------Customer Details----------- ");
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();
        Customer customer = new Customer(customerName, customerEmail);
        customers.add(customer);
        return customer;
    }

    private static Order createOrder(Scanner scanner, Customer customer, ArrayList<Product> products) {
        Order order = new Order(customer);

        while (true) {
            System.out.println("\n-------------Available products:-----------------");
            for (Product product : products) {
                System.out.println(product);
            }

            System.out.print("Enter the product ID to add to the order (or 0 to finish): ");
            int productId = scanner.nextInt();
            if (productId == 0) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Product product = products.stream()
                    .filter(p -> p.getProductId() == productId)
                    .findFirst()
                    .orElse(null);

            if (product != null) {
                order.addItem(new OrderItem(product, quantity));
            } else {
                System.out.println("Product not found!");
            }
        }

        return order;
    }

    private static void displayOrderDetails(Order order) {
        System.out.println("\nOrder Details:");
        System.out.println(order);
    }
}
