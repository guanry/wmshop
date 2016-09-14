package net.watermelon.user.manager;

import net.watermelon.user.dao.UserDao;
import net.watermelon.user.vo.SystemUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;



@Repository
public class UserDetailsServiceCustom implements UserDetailsService {
	@Autowired
	private UserDao<SystemUser> userDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SystemUser systemUser = userDao.findByLogin(username);
		SaltUser user = new SaltUser(systemUser.getLogin(), systemUser.getPass(),
				systemUser.isAvaliable(), systemUser.isAccountNotExpired(),
				systemUser.isCredentialsNotExpired(),
				systemUser.isAccountNotLocked(), systemUser.getRoles());
		
		user.setUserId(systemUser.getId());
		
		return user;
	}
	

}