package net.watermelon.product.vo;

import java.io.Serializable;

import net.watermelon.core.SearchBean;

public final class ProductSearchCriteria implements SearchBean,Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String name;
	private String reference;
	private String category;
	private String basePrice;
	private String quantity;
	private String status;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getReference() {
		return reference;
	}



	public void setReference(String reference) {
		this.reference = reference;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getBasePrice() {
		return basePrice;
	}



	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}



	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getSQL() {
			String querysql = "from ProductInformation  where 1=1";
			
			if (id != null && id.length()>0 ) {
			querysql = querysql + " and id  = " + id ;
			}
			
			if (name != null && name.length()>0) {
				querysql = querysql + " and name  like '" + name +"%'" ;
			}
		
		
	
		
		return querysql;
	}

	
}
