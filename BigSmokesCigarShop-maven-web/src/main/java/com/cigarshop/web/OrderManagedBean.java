/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.web;

import com.cigarshop.ejb.OrderManagerEJB;
import com.cigarshop.ejb.ProductManagerEJB;
import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
import com.cigarshop.entity.LineItem;
import com.cigarshop.entity.Order;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 * See discussion on http://stackoverflow.com/questions/9246141/passing-a-jsf2-managed-pojo-bean-into-ejb-or-putting-what-is-required-into-a-tra?rq=1
 * @author smalyshev
 */
@javax.inject.Named(value = "orderBean")
@javax.enterprise.context.SessionScoped
public class OrderManagedBean implements java.io.Serializable {
    @EJB
    private ProductManagerEJB productManagerEJB;
    @EJB
    private OrderManagerEJB orderManagerEJB;
    private Order cart;
    private List<Order> allOrders;
    private static final Logger LOG = Logger.getLogger(OrderManagedBean.class.getName());

    public List<Order> getAllOrders() {
        LOG.info("OrderManagedBean:getAllOrders number = " + allOrders.size() + "\n");
        return allOrders;
    }
/*
    public void setAllOrders(List<Order> allOrders) {
        this.allOrders = allOrders;
    }

*/

    /**
     * Creates a new instance of CigarsManagedBean
     */
    public OrderManagedBean() {
    }
    
    public Order getCart() {
        return cart;
    }
/*
    public void setCart(Order cart) {
        this.cart = order;
    }*/
    /**
     * Action handler - user adds cigar to cart
     * @param productId
     * @return outcome string
     */
    public String addCigarToCart(long productId)
    {
        int unitsAvailable = productManagerEJB.inquireAvailability( productId);
        //TODO: check if there is not units available and display on the UI
        //if (unitsAvailable == 0 )
            
        this.cart = orderManagerEJB.addCigarToCart(productId);
        //cigar.setQuantityAvailable(unitAvailable);
        return "lineItem.xhtml";
    }
    
    public String showOrders()
    {
        this.allOrders = orderManagerEJB.findAllOrders();
        //cigar.setQuantityAvailable(unitAvailable);
        LOG.info("OrderManagedBean:showOrders number = " + allOrders.size() + "\n");
        return "order.xhtml";
    }
    
    public String showCart()
    {
        this.cart = orderManagerEJB.findCart();
        //cigar.setQuantityAvailable(unitAvailable);
        LOG.info("OrderManagedBean:showCart orderItems number = " + ( cart == null ? 0 : cart.getLineItems().size()) + "\n");
        return "lineItem.xhtml";
    }

    public String updateCigarQuantity(LineItem lineItem, int quantity)
    {
        //this.cart = orderManagerEJB.updateCigarQuantity(lineItem, quantity);
        orderManagerEJB.updateCigarQuantity( lineItem, quantity);
        //cigar.setQuantityAvailable(unitAvailable);
        LOG.info("OrderManagedBean:updateCigarQuantity lineItem = " + lineItem.getCigar().getDescription() + "\n");
        return "lineItem.xhtml";
    }

    public void showOrder(ActionEvent event)
    {
        UIParameter param = (UIParameter) event.getComponent().findComponent("showOrderId");
        Integer orderId = Integer.parseInt(param.getValue().toString());
        for(Order order : this.allOrders)
        {
            if(order.getOrderId() == orderId)
            {
                this.cart = order;
                break;
            }
        }
        //cigar.setQuantityAvailable(unitAvailable);
        LOG.info("OrderManagedBean:showOrder " + orderId + " items = " + ( cart == null ? 0 : cart.getLineItems().size()) + "\n");
        //return "lineItem.xhtml";
    }

    public void removeOrder(ActionEvent event) 
    {
        UIParameter param = (UIParameter) event.getComponent().findComponent("deleteOrderId");
        Integer id = Integer.parseInt(param.getValue().toString());
        orderManagerEJB.removeOrder(id);
        this.cart = orderManagerEJB.findCart();
        this.allOrders = orderManagerEJB.findAllOrders();
    }
}
