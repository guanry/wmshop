package net.watermelon.user.dao;




import java.util.List;

import net.watermelon.user.vo.SystemUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserDao<T> extends JpaRepository<SystemUser, Integer>{

	SystemUser  findByLogin(String login);
	List <SystemUser> findByGroupId(Integer groupId);
		 
}
