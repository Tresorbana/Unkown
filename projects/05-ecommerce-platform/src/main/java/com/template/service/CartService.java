package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;

    public CartService(CartItemRepository cartItemRepo, ProductRepository productRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo  = productRepo;
    }

    public CartItem addToCart(Customer customer, Long productId, int quantity) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        return cartItemRepo.findByCustomerAndProduct(customer, product)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + quantity);
                    return cartItemRepo.save(existing);
                })
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCustomer(customer);
                    item.setProduct(product);
                    item.setQuantity(quantity);
                    return cartItemRepo.save(item);
                });
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotal(Customer customer) {
        List<CartItem> items = cartItemRepo.findByCustomer(customer);
        return items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clearCart(Customer customer) {
        cartItemRepo.deleteByCustomer(customer);
    }
}
