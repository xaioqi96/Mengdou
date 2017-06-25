package com.md.pages;

import java.util.Collections;
import java.util.List;

/**
 * 分页基本数据包装类 
 * <p>
 *
 * @date	 2017年3月14日
 * @version  V1.0.0
 */
public class Page {

	public static final int DEFAULT_PAGE_SIZE = 10;

	// 每页显示数
	private int pageSize;

	// 当前页码
	private int currentPage;

	private int prePage;

	private int nextPage;

	// 总页数
	private int totalPage;

	// 总记录数
	private int total;

	// 查询出的当前页记录  
	private List<?> rows = Collections.emptyList();


	public Page() {
		this.currentPage = 1;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}


	public Page( int currentPage, int pageSize ) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage( int currentPage ) {
		this.currentPage = currentPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}


	public int getPrePage() {
		return prePage;
	}


	public void setPrePage( int prePage ) {
		this.prePage = prePage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setNextPage( int nextPage ) {
		this.nextPage = nextPage;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage( int totalPage ) {
		this.totalPage = totalPage;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal( int total ) {
		this.total = total;
	}


	public List<?> getRows() {
		return rows;
	}


	public void setRows( List<?> rows ) {
		this.rows = rows;
	}


}
