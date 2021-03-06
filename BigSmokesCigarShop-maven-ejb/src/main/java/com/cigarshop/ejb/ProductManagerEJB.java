/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.ejb;

import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
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
import javax.ejb.Remote;
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
@Remote(ProductManager.class)
//@LocalBean
public class ProductManagerEJB implements ProductManager {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private InventorySystem inventory;
    private static final Logger logger = Logger.getLogger(
                "com.cigarshop.ejb.ProductManagerEJB");

    @Override
    public Category createCategory(String code, String name)
    {
        Category cat;
        try {
            cat = new Category(code, name);
            logger.log(Level.INFO, "Created Category {0}-{1}",new Object[] { code, name });
            em.persist(cat);
            logger.log(Level.INFO, "Persisted Category {0}-{1}",new Object[] { code, name });
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
        return cat;
    }
    
    @Override
    public Cigar createCigar(String categoryCode, String name, String desc, Double price)
    {
        Cigar cigar;
        try {
            Category category = em.find(Category.class, categoryCode);
            cigar = new Cigar( category, name, desc, price);
            logger.log(Level.INFO, "Created Cigar {0}-{1}-{2}-{3}-{4}",new Object[] { cigar.getProductId(), categoryCode, name, desc, price });
            em.persist(cigar);
            logger.log(Level.INFO, "Persisted Cigar {0}-{1}-{2}-{3}-{4}",new Object[] { cigar.getProductId(), categoryCode, name, desc, price });
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
        return cigar;
    }
    
    @Override
    public List<Category> listCigarCategories()
    {
        Query query = em.createNamedQuery("Category.findAll");
        return query.getResultList();
    }
    
    @Override
    public List<Cigar> findCigarsByCategory(String cat)
    {
        Query query = em.createNamedQuery("Cigar.findByCategoryCode");
        query.setParameter("categoryCode", cat);
        return query.getResultList();
    }
    
    @Override
    public Cigar getProductDetails(long productId)
    {
        //InventorySystemGateway inventory = new InventorySystemGateway();
        int unitAvailable = inventory.inquireProductAvailability(productId);
        
        Cigar cigar = em.find(Cigar.class, productId);
        cigar.setQuantityAvailable(unitAvailable);
        return cigar;
    }
}
