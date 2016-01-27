package net.watermelon.core;

import javax.servlet.http.HttpServletRequest;

/**
 * 这里修改基本的每页数量
 * 
 * @author Administrator
 * 
 */
public class PageParam {
	private int start;
	private int limit;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 获得页面传递的翻页参数，为翻页做预备。
	 * 
	 * @param request
	 * @return
	 */

	static public PageParam getPagePara(HttpServletRequest request) {
		String l = request.getParameter("limit");
		String p = request.getParameter("page");
		if (l == null)
			l = BaseSet.pageSize;
		if (p == null)
			p = "0";
		int limit = Integer.parseInt(l);
		int page = Integer.parseInt(p);
		int start = (page - 1) * limit;
		PageParam pageParam = new PageParam();
		pageParam.setLimit(limit);
		pageParam.setStart(start);
		return pageParam;
	}

}
