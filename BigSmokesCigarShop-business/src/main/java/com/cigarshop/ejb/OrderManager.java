/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.ejb;

import com.cigarshop.entity.Category;
import com.cigarshop.entity.LineItem;
import com.cigarshop.entity.Order;
import java.util.List;

/**
 *
 * @author smalyshev
 */
public interface OrderManager {
    Order addCigarToCart(long productId);
    void updateCigarQuantity(LineItem lineItem, int quantity);
    Order findCart();
    List<Order> findAllOrders();
    void removeOrder(Integer orderId);
}
