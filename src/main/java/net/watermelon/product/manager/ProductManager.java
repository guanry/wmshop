package net.watermelon.product.manager;

import java.util.List;


import net.watermelon.product.vo.ProductInformation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;



@Service
public class ProductManager {
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<ProductInformation> findBookings(String username) {
		if (username != null) {
			return sessionFactory
					.getCurrentSession()
					.createQuery(
							"select b from Booking b where b.user.username = :username order by b.checkinDate")
					.setParameter("username", username).list();
				} else {
			return null;
		}
	}

	
}
