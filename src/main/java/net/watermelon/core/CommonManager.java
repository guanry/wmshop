package net.watermelon.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommonManager {

	@Autowired
	private CommonDAO commonDAO;
	
	
	

	public PagedList getList(String query, PageParam pageParam) {
		return commonDAO.getList(query, pageParam.getStart(),
				pageParam.getLimit());
	}

	public Object get(Class<?> cla, Serializable id) {
		return commonDAO.get(cla, id);
	}

	/**
	 * 增加或者保存，如果Object有ID的值，先查询是否存在，然后保存
	 * 
	 * @param obj
	 */

	
	@Transactional
	public void save(Object obj) {
		commonDAO.save(obj);

	}

	/**
	 * 增加
	 * 
	 * @param obj
	 */
	public void add(Object obj) {
		commonDAO.add(obj);

	}

	public void remove(Object obj) {
		commonDAO.remove(obj);
	}

	
	/**
	 * todo 需要一个传递参数查询的例子
	 * @param queryStr
	 * @return
	 */
	public List getObjectLists(String queryStr) {

		return commonDAO.getObjectLists(queryStr);
	}

	
	
	/**
	 * for dataTable
	 * @param query
	 * @param dataTableParam
	 * @return
	 */
	public DataTableVo getList(String query, DataTableParam dataTableParam) {
		
		return commonDAO.getList(query, dataTableParam);
		
	
	}




}
