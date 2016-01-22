/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.web;

import com.cigarshop.ejb.ProductManager;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

import com.cigarshop.ejb.ProductManagerEJB;
import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

/**
 * See discussion on http://stackoverflow.com/questions/9246141/passing-a-jsf2-managed-pojo-bean-into-ejb-or-putting-what-is-required-into-a-tra?rq=1
 * @author smalyshev
 */
@javax.inject.Named(value = "cigarBean")
@javax.enterprise.context.SessionScoped
public class CigarManagedBean implements java.io.Serializable{
    @EJB
    private ProductManager productManagerEJB;
    private Category category;
//    private String categoryCode;
//    private Object[] selectedCategory;
    private List<Cigar> cigars;
    private Cigar selectedCigar;
    private static final Logger LOG = Logger.getLogger(CigarManagedBean.class.getName());

    /**
     * Creates a new instance of CigarsManagedBean
     */
    public CigarManagedBean() {
    }
    
    public Cigar getSelectedCigar()
    {
        return selectedCigar;
    }
    public void setSelectedCigar(Cigar selectedCigar)
    {
        this.selectedCigar = selectedCigar;
    }
    
    public Category getCategory()
    {
        return category;
    }
    public void setCategory(Category category)
    {
        this.category = category;
    }
/*    
    public String getCategoryCode()
    {
        return categoryCode;
    }
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
*/    
    public List<Cigar> getCigars()
    {
        return this.cigars;
    }
    public void update() throws NamingException
    {
        /*
        //Try to use JNDI to connect to remote
        Properties prop = new Properties();
        //prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        prop.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
        prop.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8180"); //It not being used but local instance is return after lookup
        prop.put(Context.SECURITY_PRINCIPAL, "ejb");
        prop.put(Context.SECURITY_CREDENTIALS, "test");
        prop.put("jboss.naming.client.ejb.context", true);

        Context context = new InitialContext(prop);
        Object obj = context.lookup("ejb:app/BigSmokesCigarShop-maven-ejb-1.0-SNAPSHOT/ProductManagerEJB!com.cigarshop.ejb.ProductManager");
        if (obj != null)
        {
            LOG.info("CigarManagedBean:update JNDI lookup obj= " + obj.getClass() + "\n");
            ProductManager EJB=(ProductManager)obj;
            LOG.info("CigarManagedBean:update JNDI lookup EJB= " + EJB.getClass() + "\n");
            List<Cigar> retCigs = EJB.findCigarsByCategory(this.category.getCategoryCode());
            LOG.info("CigarManagedBean:update JNDI findCigarsByCategory EJB= " + retCigs + "\n");
        }
        else
            LOG.info("CigarManagedBean:update JNDI lookup obj is NULL\n");
        */
        //TODO: Update list on first part load with first Category
        String categoryCode = this.category.getCategoryCode();
        if (this.category == null || categoryCode == null || categoryCode.isEmpty())
            return;
        List<Cigar> retCigars = productManagerEJB.findCigarsByCategory(categoryCode);
        
        System.out.println("CigarManagedBean:update= " + retCigars + "\n");
        LOG.info("CigarManagedBean:update productManagerEJB=" + productManagerEJB.getClass() + " retCigars= " + retCigars + "\n");
        this.cigars = retCigars;
    }
    /**
     * Returns an array of SelectItem to be displayed in the Category
     * Dropdown list in the SearchCigar page
     * @return
     */
    public javax.faces.model.SelectItem[] getCategories()
    {
        SelectItem[] options = null;
        List<Category> categories = productManagerEJB.listCigarCategories();
        if (categories != null && categories.size() > 0)
        {
            this.category = categories.get(0);
            int i = 0;
            options = new SelectItem[categories.size()];
            for (Category dc : categories)
            {
                options[i++] = new SelectItem(dc.getCategoryCode(), dc.getName());
            }
        }
        return options;
    }
    /**
     * Action handler - user selects a cigar record from the list
     * (data table) to view/edit
     * @param cigar
     * @return outcome string
     */
    public String showDetails(Cigar cigar)
    {
        this.selectedCigar = productManagerEJB.getProductDetails( cigar.getProductId());
        return "DETAILS";
    }

}
