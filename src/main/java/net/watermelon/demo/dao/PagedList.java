package net.watermelon.demo.dao;

import java.util.List;

public class PagedList {
	private int totalProperty;// 总记录数
	private List<Object> list;
	private int limit = 1;
	private int start;

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> result) {
		this.list = result;
	}

	public int getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotalPage() {
		int page = totalProperty / limit;
		if ((totalProperty % limit) > 0)
			page++;
		return page;
	}

}
