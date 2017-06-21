package net.watermelon.admin.menu.web;

import java.io.Serializable;

import net.watermelon.core.SearchBean;

public final class MenuSearchCriteria implements SearchBean , Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String name;
	private String parent;
	private String href;
	private String enabled;
	
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParent() {
		return parent;
	}


	public void setParent(String parent) {
		this.parent = parent;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	

	public String getHref() {
		return href;
	}


	public void setHref(String href) {
		this.href = href;
	}


	public String getEnabled() {
		return enabled;
	}


	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


	public String getSQL() {
			String querysql = "from MenuMenu where 1=1";
			
			if (id != null && id.length()>0 ) {
			querysql = querysql + " and id  = " + id ;
			}
			
			if (name != null && name.length()>0) {
				querysql = querysql + " and name  like '" + name +"%'" ;
			}
			
			if (parent != null && parent.length()>0) {
				querysql = querysql + " and parent  = " + parent ;
			}
			
			if (href != null && href.length()>0) {
				querysql = querysql + " and href   like '" + href +"%'"  ;
			}
			
			if (enabled != null && enabled.length()>0) {
				querysql = querysql + " and enabled  = " + enabled ;
			}
		
	
		
		return querysql;
	}

	
}
