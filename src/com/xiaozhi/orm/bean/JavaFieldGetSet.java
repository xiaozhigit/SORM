package com.xiaozhi.orm.bean;



/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年5月12日 下午10:30:14 
* 类的set和get方法及字段信息

*/
public class JavaFieldGetSet {
	/**
	 * 属性的源码信息.如：private int fileInfo;
	 */
	private String fieldInfo;
	/**
	 * get方法的源码信息
	 */
	private String getInfo;
	/**
	 * set方法的源码信息
	 */
	private String setInfo;
	
	
	public JavaFieldGetSet() {
	}
	
	public String getFieldInfo() {
		return fieldInfo;
	}
	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	public String getGetInfo() {
		return getInfo;
	}
	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}
	public String getSetInfo() {
		return setInfo;
	}
	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}
	public JavaFieldGetSet(String fieldInfo,String getInfo,String setInfo) {
		this.fieldInfo=fieldInfo;
		this.getInfo=getInfo;
		this.setInfo=setInfo;
	}
	  public String toString() {
		  return super.toString();
	  }

}
