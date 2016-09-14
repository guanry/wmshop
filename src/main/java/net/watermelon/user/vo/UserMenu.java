package net.watermelon.user.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_menu")
public class UserMenu {

	@Id
	@Column(name = "ID") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //自动编号的类型，要不无法保存后无法从数据库获得ID 这个必须要写的
	private int id;
	
   @Column(name = "USER_ID") 
   private String userId;
	
   @Column(name = "MENU_ID") 
   private int menuId;

	
   
   
   
   
   public UserMenu(){
	   
   }
   public UserMenu(int menuid, String userid) {
	   this.menuId = menuid;
	   this.userId = userid;
	}

public String getUserId() {
	return userId;
   }

   public void setUserId(String userId) {
	this.userId = userId;
}

   public int getMenuId() {
	return menuId;
}
public void setMenuId(int menuId) {
	this.menuId = menuId;
}
   
}
