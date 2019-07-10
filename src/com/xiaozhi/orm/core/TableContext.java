package com.xiaozhi.orm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.xiaozhi.orm.bean.ColumnInfo;
import com.xiaozhi.orm.bean.TableInfo;
import com.xiaozhi.orm.utils.JavaFileUtils;
import com.xiaozhi.orm.utils.StringUtils;

/**
 * 管理数据库
 * @author xz
 *
 */
public class TableContext {
	/**
	 * 表名为key表信息对象为value
	 */
	public static Map<String,TableInfo> tables=new HashMap<String,TableInfo>();
	/**
	 * 将po的class对象和表信息关联起来，便于重用 
	 */
	public static Map<Class,TableInfo> poClassTableMap=new HashMap<Class,TableInfo>();
	private TableContext() {};
	
	static {
		try {
			// 初始化获得表信息
			Connection con = DBManager.getConn();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[] {"TABLE"});

			while (tableRet.next()) {
				String tableName = (String) tableRet.getObject("TABLE_NAME");//获取表名
				TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), new HashMap<String, ColumnInfo>());
				//将表名和对象存入集合中
				tables.put(tableName, ti);

				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");// 查询表中所有的字段
				while (set.next()) {
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"),1);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);// 查询表中的主键
				while (set2.next()) {
					ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(0);// 设置为主键
					ti.getPriKeys().add(ci2);
				}
				if (ti.getPriKeys().size() > 0) {
					// 取唯一主键方便使用，如果是联合主键则设为空
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Map<String,TableInfo> getTableInfos(){
		return tables;
	}
	/**
	 * 从数据中获取表信息后根据配置的实体类路径创建javabean实体类对象
	 */
	public static void updateJavaPOFile() {
		Map<String,TableInfo> map=TableContext.tables;
		for(TableInfo t:map.values()) {
			JavaFileUtils.createJavaPOFile(t,new MysqlTypeConvertor());
		}
	}
	/**
	 * 加载po下的类，将实体类对象与其class对象对应
	 */
	public static void loadPOYables() {
		for(TableInfo tableInfo:tables.values()) {
			try {
				Class c=Class.forName(DBManager.getConf().getPoPackage()+"."
			+StringUtils.firstChar2UpperCase(tableInfo.getName()));
				poClassTableMap.put(c, tableInfo);
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	} 
}
