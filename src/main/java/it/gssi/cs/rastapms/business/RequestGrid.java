package it.gssi.cs.rastapms.business;

import java.util.List;


public class RequestGrid {

	private String draw;
	private Long start;
	private Long length;
	private SearchType search;
	private List<OrderType> order;
	private List<ColumnType> columns;
	

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public SearchType getSearch() {
		return search;
	}

	public void setSearch(SearchType search) {
		this.search = search;
	}

	public List<OrderType> getOrder() {
		return order;
	}

	public void setOrder(List<OrderType> order) {
		this.order = order;
	}

	public List<ColumnType> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnType> columns) {
		this.columns = columns;
	}

	public String getSortCol() {
		OrderType orderType = this.order.get(0);
		return columns.get(orderType.getColumn()).getData();
	}
	
	public String getSortDir() {
		OrderType orderType = this.order.get(0);
		return orderType.getDir();
	}

	public Long getStartPage() {
		return this.start / this.length;
	}
}
