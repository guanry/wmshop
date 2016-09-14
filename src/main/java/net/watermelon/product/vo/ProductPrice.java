package net.watermelon.product.vo;

public class ProductPrice {
	private  String wholesalePrice; //仓库价格是货物的买入价格，不包括税
	private  String retailPrice;//  零售价格
	private String unitPrice ; //打包的物品，单个的价格
	private String taxRule;// 对应不同的税率 US-PR Rate 5.5%
	private  String unitName;  //单个销售的单位
	private String onSale ;//是否显示 Onsale的图标
	public String getWholesalePrice() {
		return wholesalePrice;
	}
	public void setWholesalePrice(String wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTaxRule() {
		return taxRule;
	}
	public void setTaxRule(String taxRule) {
		this.taxRule = taxRule;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getOnSale() {
		return onSale;
	}
	public void setOnSale(String onSale) {
		this.onSale = onSale;
	}
	
}
