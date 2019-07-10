package com.xiaozhi.orm.utils;

import java.lang.reflect.Method;

/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年6月16日 上午12:17:33 
* 类说明  封装反射常用的操作

*/
public class ReflectUtils {
	 	/**
	 	 * 调用obj对象对应属性filedName的方法
	 	 * @param c
	 	 * @param fieldName
	 	 * @param obj
	 	 * @return
	 	 */
		public static Object invokeGet(String fieldName,Object obj) {
			try {
				Class c=obj.getClass();
				Method m=c.getMethod("get"+StringUtils.firstChar2UpperCase(fieldName), null);
				 	m.setAccessible(true);
				return m.invoke(obj,null);
			}catch(Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * 调用obj对象对应属性filedName的set方法
		 * @param obj 调用对象
		 * @param columnName  字段名
		 * @param columnValue 字段值
		 */
		public static void invokeSet(Object obj,String columnName,Object columnValue) {
			try {
				if(columnValue==null||columnValue.equals("")) {return ;};
				Method m=obj.getClass().getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(columnName),
						columnValue.getClass());
				m.setAccessible(true);
				m.invoke(obj,columnValue);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
}
