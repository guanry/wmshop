package net.watermelon.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;



/**
 * 系统用户表
 * @author samsung
 *
 */
@Entity
@Table(name = "WM_USERS")
public class SystemUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String login;

	 private String pass;
	 @NotEmpty
	 private String nicename;
	 @Email
	 private String email;
	 
	 
	 @DateTimeFormat(iso=ISO.DATE)
	 private Date registered;

	
	private boolean avaliable;

	@DateTimeFormat(iso=ISO.DATE)
	private Date lastlogin;
	 

	private String headPic;
	
	//账号是否过期
	private  boolean  accountNotExpired;
	
	
	//证书过期
	private   boolean credentialsNotExpired;
	
	
	//账号是否锁定
	private  boolean accountNotLocked ;
	
	//所在系统的组织机构
	private Integer groupId;
	
	
	//是否该组织领导
	private boolean empIsLeader;
	
	
	//@OneToMany(fetch = FetchType.EAGER)
 //@OneToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "WM_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false, insertable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
//	@Where(clause = "post_type='product' and post_status='publish'") //加入where 条件
//	@OrderBy(clause = "term_order  asc")
// DETACH 删多对多表的关系， DELETE就全删除了。
//	@Cascade(org.hibernate.annotations.CascadeType.DETACH)
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "WM_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false, insertable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@Cascade(org.hibernate.annotations.CascadeType.DETACH)
	private List<SystemRole> roles  = new ArrayList<SystemRole>();


	
	public List<SystemRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}
	public boolean isAvaliable() {
		return avaliable;
	}
	public boolean isAccountNotExpired() {
		return accountNotExpired;
	}
	public void setAccountNotExpired(boolean accountNotExpired) {
		this.accountNotExpired = accountNotExpired;
	}
	public boolean isCredentialsNotExpired() {
		return credentialsNotExpired;
	}
	public void setCredentialsNotExpired(boolean credentialsNotExpired) {
		this.credentialsNotExpired = credentialsNotExpired;
	}
	public boolean isAccountNotLocked() {
		return accountNotLocked;
	}
	public void setAccountNotLocked(boolean accountNotLocked) {
		this.accountNotLocked = accountNotLocked;
	}
	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
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
	public Date getRegistered() {
		return registered;
	}
	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public boolean isEmpIsLeader() {
		return empIsLeader;
	}
	public void setEmpIsLeader(boolean empIsLeader) {
		this.empIsLeader = empIsLeader;
	}
	
	
}
