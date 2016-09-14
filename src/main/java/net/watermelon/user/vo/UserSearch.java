package net.watermelon.user.vo;

import net.watermelon.core.SearchBean;

public final class UserSearch implements  SearchBean{
	
	private String login;
	 private String nicename;
	 private String email;
	 private String avaliable;
	 private String groupId;

	

	public String getGroupId() {
		return groupId;
	}



	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



	public String getLogin() {
		return login;
	}



	public void setLogin(String login) {
		this.login = login;
	}



	public String getNicename() {
		return nicename;
	}



	public void setNicename(String nicename) {
		this.nicename = nicename;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}










	public String getAvaliable() {
		return avaliable;
	}



	public void setAvaliable(String avaliable) {
		this.avaliable = avaliable;
	}



	/**
	 * 这里考虑写成MyBits类似的XML文件配置的形式。目前先这样
	 */

	public String getSQL() {
		    String querysql = "  where 1=1 ";
		
		    if (login != null && login != "") {
			querysql = querysql + " and login like  '"+ login+"%'";
			}
	
			
			if (nicename != null && nicename != "") {
				querysql = querysql + " and nicename like  '"+ nicename+"%'";
				}
			
			if (email != null && email != "") {
				querysql = querysql + " and email like  '"+ email+"%'";
				}
			
			if (avaliable != null && avaliable != "" ) {
				querysql = querysql + " and available =  "+ avaliable;
				}
			
			if (groupId != null && groupId != "" ) {
				querysql = querysql + " and groupId =  "+ groupId;
				}
		
		return querysql;
	} 

	
}
