package net.watermelon.user.vo;

import net.watermelon.core.SearchBean;

public final class RoleSearch implements  SearchBean{
	
	private String name;
	 private String text;


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





	/**
	 * 这里考虑写成MyBits类似的XML文件配置的形式。目前先这样
	 */

	public String getSQL() {
		    String querysql = "  where 1=1 ";
		    if (name != null && name != "") {
			querysql = querysql + " and name like  '"+ name+"%'";
			}
			if (text != null && text != "") {
				querysql = querysql + " and text like  '"+ text+"%'";
				}
		return querysql;
	} 

	
}
