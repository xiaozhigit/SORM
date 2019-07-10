package com.xiaozhi.orm.core;
/**
 * 负责java数据类型和数据库类型的转化
 * @author xz
 *
 */
public interface TypeConvertor {
	/**
	 * 将数据库类型转换为java类型
	 * @param columnType
	 * @return
	 */
  public String datebaseType2JavaType(String columnType);
  /**
   * 将java数据类型转换为数据库数据类型
   * @param javaDataType
   * @return
   */
  public String JavaType2datebaseType(String javaDataType);
  
}
