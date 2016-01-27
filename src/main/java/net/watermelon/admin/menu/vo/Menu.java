package net.watermelon.admin.menu.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

//import org.hibernate.validator.constraints.Email;


@Entity
@Table(name = "menu_menu")
public class Menu {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 4, max = 20, message = "菜单名称长度必须位于5到20之间") 
	@Column(name = "name")
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

}
