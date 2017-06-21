package net.watermelon.org.dao;


import java.util.List;


import net.watermelon.org.vo.Organization;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface OrganizationDao<T> extends JpaRepository<Organization, Integer>{
		 Organization  findByTreeId(Integer treeId);
		 @Query("from Organization where treeId is null") 
	   	 public List<Organization> getUnTreeOrg(); 
	
}
