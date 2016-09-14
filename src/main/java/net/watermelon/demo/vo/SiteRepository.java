package net.watermelon.demo.vo;

import java.util.List;

import net.watermelon.core.DataTableParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface SiteRepository<T> extends JpaRepository<Site, Integer>{
	

	@SuppressWarnings("unchecked")
	public Site save(Site site);
	
	 public List<Site> findAll();
	 
	 Page<Site> findAll(Specification<Site> spec, Pageable pageable);
	 
	 
	 
     /**@Query("from AccountInfo a where a.accountId = :id") 
	 public Site findByAccountId(@Param("id") Long accountId); 

	   @Query("from AccountInfo a where a.balance > :balance") 
		   public Page<Site> findByBalanceGreaterThan( 
	 @Param("balance")Integer balance,Pageable pageable); 
**/

  @Query("from Site a where a.name like  :name% ") 
   public Page<Site> getList(@Param("name")String name,Pageable pageable); 
    
  
	 
}
