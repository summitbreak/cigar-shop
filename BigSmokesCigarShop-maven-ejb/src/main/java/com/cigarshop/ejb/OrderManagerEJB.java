/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.ejb;

import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
import com.cigarshop.entity.LineItem;
import com.cigarshop.entity.Order;
import com.cigarshop.entity.OrderStatus;
import com.cigarshop.helper.InventorySystem;
import com.cigarshop.helper.InventorySystemGateway;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author smalyshev
 */
@Stateless
@LocalBean
//@Named
public class OrderManagerEJB {
    @PersistenceContext
    private EntityManager em;
    //@Inject
    //private InventorySystem inventory;
    private static final Logger logger = Logger.getLogger(
                "com.cigarshop.ejb.OrderManagerEJB");

    public List<Category> listCigarCategories()
    {
        Query query = em.createNamedQuery("Category.findAll");
        return query.getResultList();
    }

    public Order addCigarToCart(long productId)
    {
        InventorySystem inventory = new InventorySystemGateway();
        int unitAvailable = inventory.inquireProductAvailability((int)productId);
        //TODO: define Customer, add its foreigh key to Order table
        //TODO: implement login for the Customer
        
            Order cart = findCart();
            if (cart == null)
            {
                cart = new Order(OrderStatus.CART, "Cart");
                em.persist(cart);
            }
            Cigar cigar = em.find(Cigar.class, productId);
            LineItem lineItem = new LineItem(cart, 1, cigar);
            cart.addLineItem(lineItem);
            logger.log(
                    Level.INFO,
                    "Added productId {0} to order ID {1}.",
                    new Object[] { productId, cart == null ? "null" : cart.getOrderId() });
        return cart;
    }
    
    public Order findCart()
    {
        Order cart = null;
        try 
        {
            Query query = em.createNamedQuery("Order.findCart");
            query.setParameter("status", OrderStatus.CART);
            List<Object> result = query.getResultList();
            if (result != null && result.size() != 0 && result.get(0) instanceof Order)
            {
                cart = (Order)result.get(0);
            }
        } 
        catch (Exception e) 
        {
            logger.log(
                    Level.WARNING,
                    "Couldn't findCart");
            throw new EJBException(e.getMessage());
        }
        return cart;
        
    }

    public List<Order> findAllOrders()
    {
        List<Order> orders = null;
        try 
        {
            Query query = em.createNamedQuery("Order.findAllOrders");
            orders = (List<Order>)query.getResultList();
            logger.log(
                    Level.INFO,
                    "findAllOrders number {0} ",
                    new Object[] { orders.size() });
        } 
        catch (Exception e) 
        {
            logger.log(
                    Level.WARNING,
                    "Couldn't findAllOrders ");
            throw new EJBException(e.getMessage());
        }
        return orders;
    }
    public void removeOrder(Integer orderId) {
        try {
            Order order = em.find(Order.class, orderId);
            em.remove(order);
            logger.log(
                    Level.INFO,
                    "removeOrder {0} ",
                    new Object[] { orderId });
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateCigarQuantity(LineItem lineItem, int quantity)
    {
        Order order = em.find(Order.class, lineItem.getOrder().getOrderId());
        for(LineItem item : order.getLineItems())
        {
            if(item.getItemId()== lineItem.getItemId())
            {
                item.setQuantity(quantity);
                break;
            }
        }
        logger.log(
                Level.INFO,
                "updateCigarQuantity orderId {0} lineItemId {1} quantity {2}",
                new Object[] { lineItem.getOrder().getOrderId(), lineItem.getItemId(), quantity });
    }    
}
