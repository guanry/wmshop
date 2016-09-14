package net.watermelon.user.dao;

import net.watermelon.user.vo.SystemRole;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleDao<T> extends JpaRepository<SystemRole, Integer>{



		 
}
