package net.watermelon.core;

import javax.servlet.http.HttpServletRequest;

public class DataTableParam {
	private String draw;
	private int start;
	private int length;
	private String  orderby;
	private String  orderdir;
	private String  searchValue;
	

	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getOrderdir() {
		return orderdir;
	}
	public void setOrderdir(String orderdir) {
		this.orderdir = orderdir;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
   
	 public DataTableParam(HttpServletRequest request) {
		 draw = request.getParameter("draw");
		 start = Integer.parseInt(request.getParameter("start"));
		 length =  Integer.parseInt(request.getParameter("length"));
		 orderby = request.getParameter( "order[0][column]" );
		 orderby = request.getParameter("columns["+orderby+"][data]");
		 orderdir =  request.getParameter( "order[0][dir]");
		  searchValue =  request.getParameter("search[value]") ;
		if (length == 0)
			length = Integer.parseInt(BaseSet.pageSize);
	}


}
