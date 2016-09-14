package net.watermelon.product.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "shop_product_information")
public class ProductInformation {
	
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "product_type")
	private String type; //Statndard , Pack of existiing , virtual product
	@Column(name = "name")
	private String name; //The public name for this product Invalid characters <>;=#{}   注意语言的问题
	@Column(name = "reference_code")
	private String referenceCode; //your reference code for this product allowes special characters .-_#\ 
	@Column(name = "enabled")
	private String enabled; //Yes NO
	
	@Column(name = "redirct_disabled")
	private String redirctDisabled; // 404 ; 301固定显示另外一个；302 临时显示另外一个
	@Column(name = "visibility")
	private String visibility ;// everywhere; catalog only ; search only ; no where 
	@Column(name = "options")
	private String options;//available for order;show price ;online only( not sold for retail store)
	@Column(name = "condition")
	private String condition; //new  used refurbished
	@Column(name = "short_description")
	private String shortDescription ;// 简要描述 apprars in the product list and at the top of  the product page;
	@Column(name = "description")
	private String description; //Appears in the body of the product page
	@Column(name = "tags")
	private String tags; //will be displayed in the tag block when enabled. Tags help customers easily find your products;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getRedirctDisabled() {
		return redirctDisabled;
	}
	public void setRedirctDisabled(String redirctDisabled) {
		this.redirctDisabled = redirctDisabled;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//List
	//id,Image,name,Refrence,category,basePrice,final price,quantity;status 
	//点击进入后 显示
	
}
