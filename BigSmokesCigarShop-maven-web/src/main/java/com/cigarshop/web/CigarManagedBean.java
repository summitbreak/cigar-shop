/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.web;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

import com.cigarshop.ejb.ProductManagerEJB;
import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
import com.cigarshop.helper.InventorySystemGateway;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.enterprise.context.SessionScoped ;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * See discussion on http://stackoverflow.com/questions/9246141/passing-a-jsf2-managed-pojo-bean-into-ejb-or-putting-what-is-required-into-a-tra?rq=1
 * @author smalyshev
 */
//@Named(value = "cigarBean")
//@Dependent
@ManagedBean(name = "cigarBean")
@SessionScoped
public class CigarManagedBean implements java.io.Serializable{
    @EJB
    private ProductManagerEJB productManagerEJB;
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
    public void update()
    {
        //TODO: Update list on first part load with first Category
        String categoryCode = this.category.getCategoryCode();
        if (this.category == null || categoryCode == null || categoryCode.isEmpty())
            return;
        List<Cigar> retCigars = productManagerEJB.findCigarsByCategory(categoryCode);
        
        System.out.println("CigarManagedBean:update= " + retCigars + "\n");
        LOG.info("CigarManagedBean:update= " + retCigars + "\n");
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
        this.selectedCigar = cigar;
        int unitAvailable = productManagerEJB.inquireAvailability( cigar.getProductId());
        cigar.setQuantityAvailable(unitAvailable);
        return "DETAILS";
    }

}
