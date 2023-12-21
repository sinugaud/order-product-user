# Order Product User Microservices

This project utilizes microservice architecture and consists of three services: `order-service`, `product-service`, and `user-service`.

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

- **Config Server**: Configuration server setup.
- **API Gateway**: Run the gateway service and you access all the service in one port  api gateway running on port number 8085.
   suppose we want to access order-service  http://localhost:8085/ORDER_SERVICE/api/orders

