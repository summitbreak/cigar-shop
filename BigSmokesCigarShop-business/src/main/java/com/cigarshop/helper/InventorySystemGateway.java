/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.helper;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author smalyshev
 */
// Since in beans.xml bean-discovery-mode="annotated"- have to use @Dependent fr this bean to be discovered
@Dependent
public class InventorySystemGateway implements InventorySystem 
{
/*    public InventorySystemGateway()
    {
        
    }
  */  
    @Override
    public int inquireProductAvailability(long productId)
    {
        if (productId == 1)
            return 12;
        if (productId == 2)
            return 10;
        if (productId == 4)
            return 0;
        return 14;
    }

}
