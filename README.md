# Order Product User Microservices

This project utilizes microservice architecture and consists of three services: `order-service`, `product-service`, and `user-service`.

## Microservices Application Setup and Execution Guide

### Prerequisites:
- **Java Development Kit (JDK)** installed.
- Dependencies for **Spring Boot** and **Spring Cloud** included in the project.

### Steps to Run the Application:

1. **Eureka Server:**
  - Start Eureka Server, acting as the service registry.
  - Use `@EnableEurekaServer` in the main Spring Boot application class.
  - Run the Eureka Server application.

2. **Config Server:**
  - Configure a Git repository or local file system to store configuration files.
  - Use `@EnableConfigServer` in the main Spring Boot application class.
  - Run the Config Server application.

3. **API Gateway:**
  - Set up an API Gateway (e.g., Spring Cloud Gateway or Netflix Zuul).
  - Define routing and filters for incoming requests.
  - Run the API Gateway application.

4. **Additional Services:**
  - Start other microservices (e.g., Order Service, Product Service, User Service, etc.).
  - Configure services to connect to Eureka Server and Config Server.

### Running Order of Services:
1. **Eureka Server:** Ensure it's running and available.
2. **Config Server:** Start to provide configurations for other services.
3. **API Gateway:** Start to route incoming requests.
4. **Remaining Services:** Start other microservices after Eureka, Config, and API Gateway are up.

### Running Services Locally:

- Start each service separately using commands like `mvn spring-boot:run` or an IDE.
- Configure services to use Eureka Server for service registration and Config Server for configurations.
- Use different ports for each service to prevent conflicts.
- Ensure proper registration with Eureka and accessibility through the API Gateway.

Remember to configure settings such as port numbers, Eureka, and Config Server URLs in `application.properties` or `application.yml` for each service to enable seamless communication and service discovery.

Ensure compatibility among Spring Boot, Spring Cloud, and library versions for smooth operation of the microservices ecosystem.


## Order Service

### Entities
- `Order`
    - `userId`
    - `date`
    - `status`
    - `totalAmount`
    - `orderItems`

- `OrderItem`
    - `id`
    - `order`
    - `productId`
    - `quantity`
    - `price`

### Endpoints

#### OrderController

- `GET /api/orders`: Get all orders.
- `GET /api/orders/product`: Get all products related to orders.
- `GET /api/orders/{orderId}`: Get a specific order by ID.
- `POST /api/orders`: Create a new order.
- `PUT /api/orders/{orderId}`: Update an existing order.
- `PUT /api/orders/{orderId}/status`: Update order status.
- `DELETE /api/orders/{orderId}`: Cancel an order.
- `GET /api/orders/order-item/{orderId}/{productId}`: Add an item to an order.

#### OrderItemController

- `GET /order-items`: Get all order items.
- `POST /order-items`: Create a new order item.
- `POST /order-items`: Update an existing order item.

## Product Service

### Entity
- `Product`
    - `id`
    - `name`
    - `description`
    - `price`
    - `quantity`

### Endpoints

#### ProductController

- `GET /api/products`: Get all products.
- `GET /api/products/{id}`: Get a specific product by ID.
- `POST /api/products`: Create a new product.
- `DELETE /api/products/{id}`: Delete a product by ID.

## User Service

This service handles user authentication.

## Additional Implementations

- **Config Server**: Configuration server enable.
- **API Gateway**: Run the gateway service and you access all the service in one port  api gateway running on port number 8085.
   suppose we want to access order-service  http://localhost:8085/ORDER_SERVICE/api/orders

