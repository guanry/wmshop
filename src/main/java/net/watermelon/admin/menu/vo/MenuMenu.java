package net.watermelon.admin.menu.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


import org.hibernate.annotations.Cascade;


import org.hibernate.validator.constraints.NotEmpty;//Email;


@Entity
@Table(name = "menu_menu")
public class MenuMenu {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	 /*@todo
	  * 这里怎样国际化
	  */
	@NotEmpty(message="menu.not.empty")  
	@Column(name = "NAME")
		private String name;
	
//	@Email(message = "必须是Email 格式")
	@Column(name = "href")
	private String href;
	
	@Column(name = "father_id")
	private Integer parent;
	
	
	@Column(name = "enabled")
	private Integer enabled;
	
	
	@Column(name = "postion")
	private Integer postion;

	@Column(name= "MEMO")
	private String memo;
	
	@Column(name= "ICON")
	private String icon;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	//@OneToMany(fetch = FetchType.LAZY)
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinColumn(name = "father_id", updatable = false, insertable = false)
	@OrderBy(value="postion")
	private List<MenuMenu> children;
	
	
	public Integer getId() {
		return id;
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



	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}



	public Integer getPostion() {
		return postion;
	}

	public void setPostion(Integer postion) {
		this.postion = postion;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<MenuMenu> getChildren() {
		return children;
	}

	public void setChildren(List<MenuMenu> children) {
		this.children = children;
	}
	
	

}
