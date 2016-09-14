package net.watermelon.admin.menu.manager;

import java.util.List;

import net.watermelon.admin.menu.vo.MenuMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface MenuRepository extends JpaRepository<MenuMenu, Integer>{
	

	@SuppressWarnings("unchecked")
	public MenuMenu save(MenuMenu menu);
	
	 public List<MenuMenu> findAll();
	 
	 
     /**@Query("from AccountInfo a where a.accountId = :id") 
	 public Site findByAccountId(@Param("id") Long accountId); 

	   @Query("from AccountInfo a where a.balance > :balance") 
		   public Page<Site> findByBalanceGreaterThan( 
	 @Param("balance")Integer balance,Site pageable); 
**/
	 @Query("from MenuMenu where parent = -1 and enabled = 1") 
   	 public List<MenuMenu> getMenuList(); 
	 
	 public  List<MenuMenu> findByParentAndEnabled(Integer parent,Integer enabled);
}
