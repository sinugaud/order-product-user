package com.javapedia.productify.listenerr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javapedia.productify.dto.OrderPlacedEventDTO;
import com.javapedia.productify.service.ProductService;
import com.javapedia.productify.service.impl.InsufficientProductQuantityException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component
@Log4j2
public class OrderPlacedEventListener implements Serializable {
    @Autowired
    private ProductService productService;

//    @RabbitListener(queues = "product-service-queue")
//    public void handleOrderPlacedEvent(OrderPlacedEventDTO orderPlacedEvent, String token) throws InsufficientProductQuantityException {
//        Long orderId = orderPlacedEvent.getOrderId();
//        log.info("Order place event called  from product service", orderPlacedEvent);
//
//        productService.processOrderPlacedEvent(orderId, token);
//    }


        @RabbitListener(queues = "product-service-queue")
        public void handleOrderPlacedEvent(Message message) {
            try {

                log.info("product listener {}", message);

                byte[] body = message.getBody();
                String contentType = message.getMessageProperties().getContentType();
                OrderPlacedEventDTO orderPlacedEvent = deserializeMessage(body, contentType);

                // Log received message content for debugging
                log.info("Received OrderPlacedEventDTO listener: {}", orderPlacedEvent);

                // Assuming token is part of message properties
                String token = message.getMessageProperties().getHeader("token");
                log.info("order event  order id,:{}",orderPlacedEvent.getOrderId());

                // Process the event
                productService.processOrderPlacedEvent(orderPlacedEvent.getOrderId());


                log.info("product update in listener {}", orderPlacedEvent);

            } catch (Exception | InsufficientProductQuantityException e) {
                log.error("Error handling OrderPlacedEventDTO", e);
            }
        }

        private OrderPlacedEventDTO deserializeMessage(byte[] body, String contentType) throws IOException, IOException {
            if ("application/json".equals(contentType)) {
                log.info("deserialize event {}", "content");

                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(body, OrderPlacedEventDTO.class);
            } else {
                throw new UnsupportedOperationException("Unsupported content type: " + contentType);
            }
        }
    }

