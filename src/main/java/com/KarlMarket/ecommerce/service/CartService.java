package com.KarlMarket.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarlMarket.ecommerce.config.JwtRequestFilter;
import com.KarlMarket.ecommerce.dao.CartDao;
import com.KarlMarket.ecommerce.dao.ProductDao;
import com.KarlMarket.ecommerce.dao.UserDao;
import com.KarlMarket.ecommerce.entity.Cart;
import com.KarlMarket.ecommerce.entity.Product;
import com.KarlMarket.ecommerce.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).get();

        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if(username != null) {
            user = userDao.findById(username).get();
        }

        List<Cart> cartList = cartDao.findByUser(user);
        List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());

        if(filteredList.size() > 0) {
            return null;
        }

        if(product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }

        return null;
    }

    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}
