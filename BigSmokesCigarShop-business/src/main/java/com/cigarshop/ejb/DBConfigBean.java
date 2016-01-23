/*
 * Copyright 2013 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.ejb;

import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;


/**
 *
 * @author ian
 */
@Singleton
@Startup
public class DBConfigBean {
    @EJB
    private ProductManager productManagerEJB;

    @PostConstruct
    public void createData() {
        productManagerEJB.createCategory(
                "FL",
                "Flavoured");
        productManagerEJB.createCategory(
                "ST",
                "Standard");
        productManagerEJB.createCategory(
                "PT",
                "Petite");

        productManagerEJB.createCigar("PT", "Petite Cigar", "Petite Cigar Description", 12.00);
        productManagerEJB.createCigar("ST", "Standard Cigar", "Standard Cigar Description", 10.00);
        productManagerEJB.createCigar("FL", "Flavoured Cigar", "Flavoured Cigar Description", 14.00);
        productManagerEJB.createCigar("ST", "Standard Cigar", "Malboro Standard Cigar", 16.00);
    }

    @PreDestroy
    public void deleteData() {
    }
}
