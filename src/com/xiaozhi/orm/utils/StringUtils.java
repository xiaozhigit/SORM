package com.xiaozhi.orm.utils;
/**
 * 字符串处理工具类
 * @author xz
 *
 */
public class StringUtils {
	/**
	 * 将目标字符串首字符变为大写
	 * @param str 目标字符串
	 */
  public static String firstChar2UpperCase(String str) {
	  //abcd==Abcd
	  return str.toUpperCase().substring(0,1)+str.substring(1);
  }
}
