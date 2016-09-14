package net.watermelon.tree;



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
@Table(name = "WM_TREES")
public class Tree {
	@Id
	@Column(name = "ID")  //编号
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //自动编号的类型，要不无法保存后无法从数据库获得ID 这个必须要写的
	private int id;
	
	@Column(name = "TEXT")  //标题
	private String text;
	
	@Column(name = "ICON")  //Tree的图标
	private String icon;
	
	@Column(name = "AVAILABLE")  //是否可以修改
	private int available;
	
	public int getPos() {
		return pos;
	}

	public void setPos(int position) {
		this.pos = position;
	}

	@Column(name="POS") //排序位置
	private int pos;
	
	@Column(name = "PARENT") //上级编号 
	private int parentId;
	
	@Column(name = "STYLE") //类别 
	private String style;
	
	@Column(name = "CATALOG")   //大分类 菜单，组织机构，等等
	private String catalog;
	
	@Column(name = "LINK_ID")
	private Integer linkId;   //相关数据编号
	
		
	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinColumn(name = "PARENT", updatable = false, insertable = false)
	@OrderBy(value="pos")
	private List<Tree> children; //Tree的孩子
	
	@Transient //(name = "AUTHORITY_ID" )
	private int authorityId; //和权限相关暂时没用
	
	@Transient 
	private TreeState state = new TreeState();
	

	

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getAvailable() {
		return available;
	}

	public void setState(TreeState state) {
		this.state = state;
	}

	public TreeState getState() {
		return state;
	}

	public int getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}

	public void addChild(Tree menu){
		children.add(menu);
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
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


	public void setAvailable(int available) {
		this.available = available;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
