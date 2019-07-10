package com.xiaozhi.orm.bean;


import java.util.List;
import java.util.Map;

/**
 * @author 作者 xiaozhi
 * @version 创建时间：2019年3月26日 下午11:51:24 存储表信息
 * 
 */
public class TableInfo {
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 所有字段信息
	 */
	private Map<String, ColumnInfo> columns;
	/**
	 * 唯一主键
	 */
	private ColumnInfo onlyPriKey;
	/**
	 * 联合主键
	 */
	private List<ColumnInfo> priKeys;
	

	public List<ColumnInfo> getPriKeys() {
		return priKeys;
	}
	public void setPriKeys(List<ColumnInfo> priKeys) {
		this.priKeys = priKeys;
	}
	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}
	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}
	
	/**
	 * 将po的class对象和表信息对象关联起来，便于重用
	 */
	public TableInfo(String name, List<ColumnInfo> priKeys, Map<String, ColumnInfo> columns) {
		super();
		this.name = name;
		this.columns = columns;
		this.priKeys = priKeys;
	};
	private TableInfo() {};

}
