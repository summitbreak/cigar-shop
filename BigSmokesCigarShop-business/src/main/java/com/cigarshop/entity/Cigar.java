/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smalyshev
 */
@Cacheable(true)
@Entity
@Table(name = "SCEA_CIGAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cigar.findAll", query = "SELECT c FROM Cigar c"),
    @NamedQuery(name = "Cigar.findByProductId", query = "SELECT c FROM Cigar c WHERE c.productId = :productId"),
    @NamedQuery(name = "Cigar.findByCategoryCode", query = "SELECT c FROM Cigar c WHERE c.category.categoryCode = :categoryCode"),
    @NamedQuery(name = "Cigar.findByName", query = "SELECT c FROM Cigar c WHERE c.name = :name"),
    @NamedQuery(name = "Cigar.findByDescription", query = "SELECT c FROM Cigar c WHERE c.description = :description"),
    @NamedQuery(name = "Cigar.findByPrice", query = "SELECT c FROM Cigar c WHERE c.price = :price")})
public class Cigar implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long productId;
    /*
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;
    */
    private Category category;
    private String name;
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double price;
    //@JoinColumn(name = "CATEGORY_CODE", referencedColumnName = "CATEGORY_CODE")
    //@ManyToOne(optional = false)
    //private Category category;

    private int quantityAvailable;

    public Cigar() {
    }

    public Cigar(Category category, String name) {
        this.category = category;
        this.name = name;
    }
    public Cigar(Category category, String name, String desc, Double price) {
        this.category = category;
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    @Transient
    public int getQuantityAvailable()
    {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantity)
    {
        this.quantityAvailable=quantity;
    }
    
    /*public Category getCategory()
    {
        return this.category;
    }
    public void setCategory(Category category)
    {
        this.category=category;
    }*/
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    @ManyToOne
    @JoinColumn(name = "CATEGORY_CODE")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
/*
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
*/
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cigar)) {
            return false;
        }
        Cigar other = (Cigar) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cigarshop.entity.Cigar[ productId=" + productId + " ] name=" + name + " ]";
    }
    
}
