package com.xiaozhi.orm.core;
/**
 * 创建	Query对象的工厂类
 * @author 肖志
 * 
 */
public class QueryFactory {
	private static QueryFactory factory=new QueryFactory();
	
	private static Query prototypeObj;//原型对象
	static {
		try {
			Class c=Class.forName(DBManager.getConf().getQueryClass());//加载指定的query类
			prototypeObj=(Query)c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 创建并获取查询接口对象
	 * @return
	 */
	public static Query createQuery() {
		try {
			 TableContext.loadPOYables();
			return (Query)prototypeObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	};
}
