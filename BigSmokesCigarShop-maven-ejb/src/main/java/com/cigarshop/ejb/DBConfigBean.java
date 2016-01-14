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
    private ProductManagerEJB productManagerEJB;

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
/*
        productManagerEJB.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "1234-5678-01", 1);
        productManagerEJB.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "9876-4321-02", 2);
        productManagerEJB.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "5456-6789-03", 3);
        productManagerEJB.addPartToBillOfMaterial("SDFG-ERTY-BN", 7, "ABCD-XYZW-FF", 5);

        productManagerEJB.createVendor(
                100,
                "WidgetCorp",
                "111 Main St., Anytown, KY 99999",
                "Mr. Jones",
                "888-777-9999");
        productManagerEJB.createVendor(
                200,
                "Gadget, Inc.",
                "123 State St., Sometown, MI 88888",
                "Mrs. Smith",
                "866-345-6789");

        productManagerEJB.createVendorPart("1234-5678-01", 1, "PART1", 100.00, 100);
        productManagerEJB.createVendorPart("9876-4321-02", 2, "PART2", 10.44, 200);
        productManagerEJB.createVendorPart("5456-6789-03", 3, "PART3", 76.23, 200);
        productManagerEJB.createVendorPart("ABCD-XYZW-FF", 5, "PART4", 55.19, 100);
        productManagerEJB.createVendorPart("SDFG-ERTY-BN", 7, "PART5", 345.87, 100);

        Integer orderId = new Integer(1111);
        productManagerEJB.createOrder(
                orderId,
                'N',
                10,
                "333 New Court, New City, CA 90000");
        productManagerEJB.addLineItem(orderId, "1234-5678-01", 1, 3);
        productManagerEJB.addLineItem(orderId, "9876-4321-02", 2, 5);
        productManagerEJB.addLineItem(orderId, "ABCD-XYZW-FF", 5, 7);

        orderId = new Integer(4312);
        productManagerEJB.createOrder(
                orderId,
                'N',
                0,
                "333 New Court, New City, CA 90000");
        productManagerEJB.addLineItem(orderId, "SDFG-ERTY-BN", 7, 1);
        productManagerEJB.addLineItem(orderId, "ABCD-XYZW-FF", 5, 3);
        productManagerEJB.addLineItem(orderId, "1234-5678-01", 1, 15);
*/
    }

    @PreDestroy
    public void deleteData() {
    }
}
