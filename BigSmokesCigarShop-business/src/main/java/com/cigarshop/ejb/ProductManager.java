/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.ejb;

import com.cigarshop.entity.Category;
import com.cigarshop.entity.Cigar;
import java.util.List;

/**
 *
 * @author smalyshev
 */
public interface ProductManager {
    List<Category> listCigarCategories();
    List<Cigar> findCigarsByCategory(String cat);
    Cigar getProductDetails(long productId);
    Category createCategory(String code, String name);
    Cigar createCigar(String categoryCode, String name, String desc, Double price);
}
