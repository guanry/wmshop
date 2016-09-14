package net.watermelon.user.vo;


import java.util.Date;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;




@Entity
@Table(name = "CORE_MA_MENU")
public class Menu {
	@Id
	@Column(name = "MENU_ID") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //自动编号的类型，要不无法保存后无法从数据库获得ID 这个必须要写的
	private int id;
	
	@Column(name = "TITLE") 
	private String text;
	
	@Column(name = "ICON") 
	private String icon;
	
	@Column(name = "AVAILABLE") 
	private int available;
	
	public int getPos() {
		return pos;
	}

	public void setPos(int position) {
		this.pos = position;
	}

	@Column(name="POSITION")
	private int pos;
	
	@Column(name = "PARENT") 
	private int parentId;
	
	@Column(name = "STYL") 
	private String style;
	
	@Column(name = "CRT_DT") 
	private Date crtDt;
	
	@Column(name = "MODI_DT") 
	private Date modiDt;
	
	@Column(name = "CRT_ID") 
	private String crtId;
	
	@Column(name = "MODI_ID") 
	private String modiId;
	
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinColumn(name = "PARENT", updatable = false, insertable = false)
	@OrderBy(value="pos")
	private List<Menu> children;
	
	
	@Transient //(name = "AUTHORITY_ID" )
	private int authorityId;
	
	@Transient 
	private State state = new State();
	

	
	public State getState() {
		return state;
	}

	public int getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}

	public void addChild(Menu menu){
		children.add(menu);
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}



	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Date getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(Date crtDt) {
		this.crtDt = crtDt;
	}

	public Date getModiDt() {
		return modiDt;
	}

	public void setModiDt(Date modiDt) {
		this.modiDt = modiDt;
	}

	public String getCrtId() {
		return crtId;
	}

	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}

	public String getModiId() {
		return modiId;
	}

	public void setModiId(String modiId) {
		this.modiId = modiId;
	}


}
