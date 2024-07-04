package it.gssi.cs.rastapms.business;

import com.fasterxml.jackson.annotation.JsonView;
import it.gssi.cs.rastapms.domain.Views;

import java.util.List;

public class ResponseGrid<R> {

	@JsonView(Views.Private.class)
	private String draw;
	@JsonView(Views.Private.class)
	private long recordsTotal;
	@JsonView(Views.Private.class)
	private long recordsFiltered;
	@JsonView(Views.Private.class)
	private List<R> data;

	public ResponseGrid(String draw, long recordsTotal, long recordsFiltered, List<R> data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<R> getData() {
		return data;
	}

	public void setData(List<R> data) {
		this.data = data;
	}

}
