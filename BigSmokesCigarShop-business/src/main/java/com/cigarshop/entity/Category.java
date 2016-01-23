/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cigarshop.entity;

import java.io.Serializable;
import javax.persistence.Basic;
//import javax.persistence.Cache;
//import org.eclipse.persistence.annotations.CacheType;
//import org.eclipse.persistence.annotations.Cache;
//import org.eclipse.persistence.annotations.CacheCoordinationType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smalyshev
drop table "APP".CATEGORY;
create table "APP".CATEGORY
(
	CATEGORY_CODE VARCHAR(5) not null primary key,
	NAME VARCHAR(50) not null
);

create table "APP".CIGAR
(
	PRODUCT_ID INTEGER not null primary key,
	CATEGORY_CODE CHAR(5) not null,
	NAME VARCHAR(30),
	DESCRIPTION VARCHAR(250),
	PRICE DOUBLE
);
create table "APP".ORDER
(
	ORDER_ID LONG not null primary key,
	CATEGORY_CODE CHAR(5) not null,
	NAME VARCHAR(30),
	DESCRIPTION VARCHAR(250),
	PRICE DOUBLE
);
insert into Category (CATEGORY_CODE, NAME) VALUES ('FL', 'Flavoured');
 insert into Category (CATEGORY_CODE, NAME) VALUES ('ST', 'Standard');
insert into Category (CATEGORY_CODE, NAME) VALUES ('PT', 'Petite');
SELECT * FROM APP.CATEGORY;
insert into CIGAR (PRODUCT_ID, CATEGORY_CODE, NAME, DESCRIPTION, PRICE) VALUES (1, 'PT', 'Petite Cigar', 'Petite Cigar Description', 12.00);
insert into CIGAR (PRODUCT_ID, CATEGORY_CODE, NAME, DESCRIPTION, PRICE) VALUES (2, 'ST', 'Standard Cigar', 'Standard Cigar Description', 10.00);
insert into CIGAR (PRODUCT_ID, CATEGORY_CODE, NAME, DESCRIPTION, PRICE) VALUES (3, 'FL', 'Flavoured Cigar', 'Flavoured Cigar Description', 14.00);
SELECT * FROM APP.CIGAR;
*/
@Cacheable(true)
/*
//TODO:Caching doesn't seems to works as I still can see SQL queries against Category table
@Cache( //Use EclipseLink recommended annotation instead of standard @Cacheable which didn't seem to work eighter: http://www.eclipse.org/eclipselink/documentation/2.4/jpa/extensions/a_cache.htm 
  type=CacheType.SOFT, // Cache everything until the JVM decides memory is low.
  size=64000,  // Use 64,000 as the initial cache size.
  expiry=360000,  // 6 minutes
  coordinationType=CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS  // if cache coordination is used, only send invalidation messages.
)*/
@Entity
@Table(name = "SCEA_CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByCategoryCode", query = "SELECT c FROM Category c WHERE c.categoryCode = :categoryCode"),
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;

    public Category() {
    }

    public Category(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Category(String categoryCode, String name) {
        this.categoryCode = categoryCode;
        this.name = name;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryCode != null ? categoryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryCode == null && other.categoryCode != null) || (this.categoryCode != null && !this.categoryCode.equals(other.categoryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cigarshop.entity.Category[ categoryCode=" + categoryCode + " ] name="+ name + " ]";
    }
    
}
