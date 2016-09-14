package net.watermelon.org.vo;

import net.watermelon.core.SearchBean;

public final class OrganizationSearch implements  SearchBean{
	
	private String name;
	private String upId;
	

	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	public String getUpId() {
		return upId;
	}



	public void setUpId(String upId) {
		this.upId = upId;
	}



	/**
	 * 这里考虑写成MyBits类似的XML文件配置的形式。目前先这样
	 */

	public String getSQL() {
		    String querysql = " where 1=1 ";
		
		    if (name != null && name != "") {
			querysql = querysql + " and name like  '"+ name+"%'";
			}
	
		    if (upId != null ) {
				querysql = querysql + " and  upId  = "+ upId;
				}
			
		return querysql;
	} 

	
}
