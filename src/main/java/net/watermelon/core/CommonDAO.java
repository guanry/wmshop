package net.watermelon.core;

import java.io.Serializable;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDAO {

	@Autowired
	private BaseDAO baseDAO;

	public Object get(Class<?> cla, Serializable id) {
		return baseDAO.getOneObject(cla, id);
	}

	public void save(Object obj) {
		baseDAO.saveObject(obj);
	}

	public void remove(Object obj) {
		baseDAO.deleteObject(obj);
	}

	public PagedList getList(String sql, int start, int limit) {
		return baseDAO.getPagedList(sql, start, limit);
	}

	public void add(Object obj) {
		 baseDAO.add(obj);

	}

	public List getObjectLists(String queryStr) {
		return baseDAO.getObjectLists(queryStr);
	}

	
	
	public DataTableVo getList(String sql, DataTableParam dataTableParam) {
		return baseDAO.getPagedList(sql, dataTableParam);
	}

}
