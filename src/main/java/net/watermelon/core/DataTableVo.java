package net.watermelon.core;

import java.io.Serializable;
import java.util.List;


public class DataTableVo<T>implements Serializable {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String draw; // Client request times
	    private int recordsTotal; // Total records number without conditions
	    private int recordsFiltered; // Total records number with conditions
	    private List<T> data; // The data we should display on the page
		
		public String getDraw() {
			return draw;
		}
		public void setDraw(String draw) {
			this.draw = draw;
		}
		public int getRecordsTotal() {
			return recordsTotal;
		}
		public void setRecordsTotal(int recordsTotal) {
			this.recordsTotal = recordsTotal;
		}
		public int getRecordsFiltered() {
			return recordsFiltered;
		}
		public void setRecordsFiltered(int recordsFiltered) {
			this.recordsFiltered = recordsFiltered;
		}
		public List<T> getData() {
			return data;
		}
		public void setData(List<T> data) {
			this.data = data;
		}
	
}
