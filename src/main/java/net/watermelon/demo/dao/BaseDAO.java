package net.watermelon.demo.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;




import net.watermelon.admin.menu.vo.Menu;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
// 如果一个类被注解为@Transactional，Spring将会确保类的方法在运行在一个事务中。
public class BaseDAO<T> {

	@Autowired
	// 自动装配了,不用写SetGet
	private SessionFactory sessionFactory;

	/**
	 * 获得一个实体的值，在一般DAO中用这个。
	 * 
	 * @param classname
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 */

	@Transactional(readOnly = true)
	public Object getOneObject(Class<T> cla, Serializable id) { // 获得单个类,先定义一个Hibernate映射文件,映射类
																// 是映射类名字
		return sessionFactory.getCurrentSession().get(cla, id);
	}

	/**
	 * 这个主要是为标签类使用
	 * 
	 * @param className
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Transactional(readOnly = true)
	public Object getOneObject(String className, Serializable id)
			throws ClassNotFoundException { // 获得单个类,先定义一个Hibernate映射文件,映射类
											// 是映射类名字
		Class<?> cla = Class.forName(className);
		return sessionFactory.getCurrentSession().get(cla, id);
	}

	@Transactional(readOnly = true)
	public Object getOneObject(Class<?> cla, String id) {
		return sessionFactory.getCurrentSession().get(cla, id);
	}

	/**
	 * 获得一个翻页的列表,sql是HSQL的形式,start是开始的记录,Limit是取多少条记录.
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @return PagedList TotalProperty 是总记录数, List 是记录列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public PagedList getPagedList(String sql, int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		PagedList pageList = new PagedList();
		;
		try {
			int count = getObjectsCount(getCountQuery(sql));
			Query q = session.createQuery(sql);
			q.setCacheable(true);
			if (start < 0)
				start = 0;
			if (start > count)
				start = count - limit;
			q.setFirstResult(start);
			q.setMaxResults(limit);
			List result = q.list();
			pageList.setList(result);
			pageList.setTotalProperty(count);
			pageList.setLimit(limit);
			pageList.setStart(start);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	// 带参数的查询 select * from Event e where e.id=:id ,e.name = :name , e.name in
	// ('a','v')
	/**
	 * 获得一个带参数的查询列表,带翻页的列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public PagedList getObjects(String queryStr, String[] paramNames,
			Object[] params, int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(sessionFactory.getStatistics());
		Query query = null;
		query = session.createQuery(getCountQuery(queryStr));
		query = setQuery(query, paramNames, params);
		int count = 0;
		try {
			count = ((Long) query.list().get(0)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		query = session.createQuery(queryStr);
		query = setQuery(query, paramNames, params);
		query.setCacheable(true);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		List list = query.list();
		PagedList pageList = new PagedList();
		pageList.setList(list);
		pageList.setTotalProperty(count);
		pageList.setLimit(limit);
		pageList.setStart(start);

		return pageList;
	}

	/**
	 * 获得一个带参数的联合查询列表,带翻页的列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public PagedList getMapObjects(String queryStr, String[] paramNames,
			Object[] params, int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		PagedList pageList = null;
		try {
			query = session.createQuery(queryStr);
			query = setQuery(query, paramNames, params);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			query.setCacheable(true);
			List list = query.list();
			pageList = new PagedList();
			pageList.setList(list);
			pageList.setTotalProperty(0);
			pageList.setLimit(limit);
			pageList.setStart(start);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 获得一个带参数的查询结果
	 */
	@Transactional(readOnly = true)
	public Object getOneObject(String queryStr, String[] paramNames,
			Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryStr);
		query = setQuery(query, paramNames, params);
		query.setCacheable(true);
		return query.uniqueResult();
	}

	@Transactional(readOnly = true)
	public Object getOneObject(String queryStr) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryStr);
		query.setCacheable(true);
		return query.uniqueResult();
	}

	/**
	 * 新增,保存,或者修改
	 * 
	 * @param event
	 */
	@Transactional
	public void saveObject(Object obj) {
		sessionFactory.getCurrentSession().merge(obj);
	}

	/**
	 * 新增
	 * 
	 * @param Object
	 */
	@Transactional
	public void add(Object obj) {
		sessionFactory.getCurrentSession().save(obj);
	}

	/**
	 * 删除
	 * 
	 * @param event
	 */
	@Transactional
	public void deleteObject(Object obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	/**
	 * 带参数的删除
	 * 
	 * @param queryStr
	 * @param paramNames
	 * @param params
	 */
	@Transactional
	public void deleteObjectByParams(String queryStr, String[] paramNames,
			Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryStr);
		query = setQuery(query, paramNames, params);
		query.executeUpdate();
	}

	/**
	 * 修改
	 * 
	 * @param obj
	 */
	@Transactional
	public void updateObject(Object obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	// 获得select count(*) from 的形式,以便获得总记录数.
	@Transactional(readOnly = true)
	private String getCountQuery(String sql) {
		String lowcasesql = sql.toLowerCase();
		int fromint = lowcasesql.indexOf("from");
		String nsql = "select count(*) " + sql.substring(fromint);
		return nsql;
	}

	@SuppressWarnings("unchecked")
	private Query setQuery(Query query, String[] paramNames, Object[] params) {
		if (null != params && 0 < params.length) {
			for (int i = 0, n = params.length; i < n; i++) {
				Object obj = params[i];
				if (obj instanceof Collection)
					query.setParameterList(paramNames[i], (List) obj); //
				else
					query.setParameter(paramNames[i], obj);
			}
		}
		return query;
	}

	/**
	 * 获得一个带参数的查询列表一个列表,直接返回一个List
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List getObjectLists(String queryStr, String[] paramNames,
			Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(queryStr);
		query.setCacheable(true);
		query = setQuery(query, paramNames, params);
		List list = query.list();
		return list;
	}

	/**
	 * 通过直接SQL返回列表,返回 List
	 * 
	 * @param queryStr
	 * @return
	 */

	
	@Transactional(readOnly = true)
	public List getObjectLists(String queryStr, int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(queryStr);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setCacheable(true);
		List list = query.list();
		return list;
	}

	/**
	 * 通过直接SQL返回列表,返回 List
	 * @param <T>
	 * 
	 * @param queryStr
	 * @return
	 */

	@Transactional(readOnly = true)
	public List<T>  getObjectLists(String queryStr) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(queryStr);
		query.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		return list;
	}

	/**
	 * 获得一个查询结果的记录数,可以直接使用HSQL select count(*) from ***的形势
	 * 
	 * @param queryStr
	 * @param paramNames
	 * @param params
	 * @return
	 */
	@Transactional(readOnly = true)
	public int getObjectsCount(String queryStr, String[] paramNames,
			Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(queryStr);
		query.setCacheable(true);
		query = setQuery(query, paramNames, params);
		int count = 0;
		try {
			count = ((Long) (query.list().get(0))).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/*
	 * 返回记录的数目,直接使用HSQL
	 */
	@Transactional(readOnly = true)
	public int getObjectsCount(String queryStr) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(queryStr);
		query.setCacheable(true);
		int count = ((Long) (query.list().get(0))).intValue();
		return count;
	}

	/**
	 * 试验中的多记录更新
	 * 
	 * @param objects
	 * @param classname
	 */
	@Transactional
	public void updateObjects(List<Object> objects, String classname) {
		Session session = sessionFactory.getCurrentSession();
		Iterator<Object> iterate = objects.iterator();
		while (iterate.hasNext()) {
			session.merge(classname, iterate.next());
		}
	}

	/**
	 * 取得设定数量的记录
	 * 
	 * @param sqlStr
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<?> getCountObjects(String sqlStr, int count) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(sqlStr);
		query.setMaxResults(count);
		query.setCacheable(true);
		List<?> list = query.list();
		return list;
	}

	/**
	 * 执行update
	 * 
	 * @param sqlStr
	 * @return
	 */
	@Transactional
	public int updateSQLStr(String sqlStr) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(sqlStr);
		int count = query.executeUpdate();
		return count;
	}
	
	@Transactional
	public int updateSQLStr(String sqlStr,String[] paramNames,
			Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		query = session.createQuery(sqlStr);
		query = setQuery(query, paramNames, params);
		int count = query.executeUpdate();
		return count;
	}


}