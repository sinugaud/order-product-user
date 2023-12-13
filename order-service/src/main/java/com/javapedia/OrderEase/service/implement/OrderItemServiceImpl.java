package com.javapedia.OrderEase.service.implement;

import com.javapedia.OrderEase.controller.OrderItemNotFoundException;
import com.javapedia.OrderEase.dto.Product;
import com.javapedia.OrderEase.model.OrderItem;
import com.javapedia.OrderEase.repository.OrderItemRepository;
import com.javapedia.OrderEase.service.OrderItemService;
import com.javapedia.OrderEase.service.productdelegate.ProductServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductServiceDelegate productServiceDelegate;


    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItem() throws OrderItemNotFoundException {
        List<OrderItem> all = orderItemRepository.findAll();
        List<OrderItem> validOrderItems = new ArrayList<>();

        for (OrderItem item : all) {
            Product product = productServiceDelegate.getProductById(item.getProductId());

            if (product == null) {
                throw new OrderItemNotFoundException("Product with ID " + item.getProductId() + " not found");
            } else {
                validOrderItems.add(item); // If product details are found, add to the list of valid order items
            }
        }

        return validOrderItems; // Return only the valid order items
    }

    @Override
    public OrderItem updateOrderItem(OrderItem updatedOrderItem) throws OrderItemNotFoundException {

        OrderItem item = new OrderItem();
        // Update the existing OrderItem with the new details
        item.setProductId(updatedOrderItem.getProductId());
        item.setQuantity(updatedOrderItem.getQuantity());

        // Save the updated OrderItem
        return orderItemRepository.save(item);
    }


}
