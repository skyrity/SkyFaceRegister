package com.skyrity.bean;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

	private static final long serialVersionUID = -3707753006881342736L;
	public static final int PAGE_NUM_DEFAULT = 1;
	public static final int PAGE_SZIE_DEFAULT = 10;

	/** 每页显示多少条记录 */
	private int pageSize;
	/** 当前第几页数据 */
	private int currentPage;
	/** 一共多少条记录 */
	private int totalRecord;
	/** 一共多少页 */
	private int totalPage;
	/** 要显示的数据 */
	private List<T> dataList;

	public Pager() {
		super();
	}

	public Pager(int pageNum, int pageSize, List<T> sourceList) {
		super();

		// 当前第几页数据
		this.currentPage = pageNum;
		// 每页显示的记录数
		this.pageSize = pageSize;

		if (sourceList == null) {
			return;
		}
		// 总记录数
		this.totalRecord = sourceList.size();
		// 获取总页数
		this.totalPage = this.totalRecord / this.pageSize;
		if (this.totalRecord % this.pageSize != 0) {
			this.totalPage = this.totalPage + 1;
		}
		// 当前第几页数据
		this.currentPage = this.totalPage < pageNum ? this.totalPage : pageNum;
		// 开始索引
		int fromIndex = this.pageSize * (this.currentPage - 1);
		// 结束索引
		int toIndex = this.pageSize * this.currentPage > this.totalRecord ? this.totalRecord
				: this.pageSize * this.currentPage;

		this.dataList = sourceList.subList(fromIndex, toIndex);
	}

	public Pager(int pageNum, int pageSize, int totalRecord, int totalPage,
                 List<T> dataList) {
		super();
		this.pageSize = pageSize;
		this.currentPage = pageNum;
		this.totalRecord = totalRecord;
		this.totalPage = totalPage;
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "Pager [pageSize=" + pageSize + ", currentPage=" + currentPage
				+ ", totalRecord=" + totalRecord + ", totalPage=" + totalPage
				+ ",dataList"+ this.dataList.toString() + "]";
	}

	/**
	 * 每页显示多少条记录
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:53:49
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 每页显示多少条记录
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:53:52
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 当前第几页数据
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:07
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 当前第几页数据
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:12
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 一共多少条记录
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:34
	 * @return
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 一共多少条记录
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:39
	 * @param totalRecord
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	/**
	 * 一共多少页
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:53
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 一共多少页
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:54:56
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 要显示的数据
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:55:07
	 * @return
	 */
	public List<T> getDataList() {
		return dataList;
	}

	/**
	 * 要显示的数据
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-4-6上午11:55:10
	 * @param dataList
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
