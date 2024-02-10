package  com.javapedia.OrderEase.controller;
import com.javapedia.OrderEase.model.Cart;
import com.javapedia.OrderEase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")

public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Cart>> addToCart(@RequestBody List<Cart> cartItems) {
        List<Cart> updatedCart = cartService.addToCart(cartItems);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Cart>> getAllCart() {
        List<Cart> allCart = cartService.getAllCart();
        return new ResponseEntity<>(allCart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        Boolean deleted = cartService.deleteCart(id);
        if (deleted) {
            return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cart item not found", HttpStatus.NOT_FOUND);
        }
    }
}
