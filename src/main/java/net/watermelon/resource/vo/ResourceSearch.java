package net.watermelon.resource.vo;

import net.watermelon.core.SearchBean;

public final class ResourceSearch implements  SearchBean{
	
	private String name;
	private String text;
	private String language;
	

	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	/**
	 * 这里考虑写成MyBits类似的XML文件配置的形式。目前先这样
	 */

	public String getSQL() {
		    String querysql = " where 1=1 ";
		
		    if (name != null && name != "") {
			querysql = querysql + " and name like  '"+ name+"%'";
			}
	
			
			if (text != null && text != "") {
				querysql = querysql + " and text like  '"+ text+"%'";
				}
			
			if (language != null && language != "") {
				querysql = querysql + " and language like  '"+ language+"%'";
				}
		
		return querysql;
	} 

	
}
