package net.watermelon.org.vo;


/**
 * 组织机构管理
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "core_org")
public class Organization {
	@Id
	@Column(name = "ID") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //自动编号的类型，要不无法保存后无法从数据库获得ID 这个必须要写的
	private Integer id;
	

	@NotEmpty
	@Size(min = 3)
	@Column(name = "org_name") 
	private String name;
	
	@Column(name = "ORG_CODE") 
	private String code;
	
	
	
	@Column(name = "TREE_ID")
	private Integer treeId;  //树形节点编号  树形结构都要这个
	


     @NotEmpty
	 @Size(min = 5)
	@Column(name="TEXT")
	private String text;
	
	@Column(name="ADDR")
	private String addr;
	
	@Transient 
	private int pos;
	
	
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getTreeId() {
		return treeId;
	}

	public void setTreeId(Integer treeId) {
		this.treeId = treeId;
	}

	
}
