package com.xiaozhi.orm.core;
/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年5月12日 下午6:52:45 
* mysql数据库类型与java类型转换实现类
*/
public class MysqlTypeConvertor implements TypeConvertor {

	@Override
	public String datebaseType2JavaType(String columnType) {
		if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)) {
			return "String";
		}else if("int".equalsIgnoreCase(columnType)||"tinyint".equalsIgnoreCase(columnType)
				||"smallint".equalsIgnoreCase(columnType)||"integer".equalsIgnoreCase(columnType)
				||"bit".equalsIgnoreCase(columnType)) {
			return "Integer";
		}else if("bigint".equalsIgnoreCase(columnType)) {
			return "Long";
		}else if("double".equalsIgnoreCase(columnType)||"float".equalsIgnoreCase(columnType)
				||"decimal".equalsIgnoreCase(columnType)) {
			return "Double";
		}else if("date".equalsIgnoreCase(columnType)) {
			return "java.sql.Date";
		}else if("time".equalsIgnoreCase(columnType)) {
			return "java.sql.Time";
		}else if("timestamp".equalsIgnoreCase(columnType)||"datetime".equalsIgnoreCase(columnType)) {
			return "java.sql.Timestamp";
		}
		return null;
	}

	@Override
	public String JavaType2datebaseType(String javaDataType) {
		return null;
	}

}
