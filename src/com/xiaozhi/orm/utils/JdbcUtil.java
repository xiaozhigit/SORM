package com.xiaozhi.orm.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


/**
 * 数据库工具类
 * @author xz
 *
 */
public class JdbcUtil {
	
	static Properties pros=null;//处理资源文件信息
	
	 static { //加载jdbcUtil时调用
		 pros=new Properties();
		 try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 /**
	  * 连接mysql数据库
	  * @return
	  */
	public static Connection getMysqlConn() {
		try {
			Class.forName(pros.getProperty("mysqlDriver"));
			return DriverManager.getConnection(pros.getProperty("mysqlURL"),
					pros.getProperty("mysqlUser"),pros.getProperty("mysqlPwd"));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 处理sql设置参数
	 * @param ps 预编译sql语句对象
	 * @param params 参数
	 */
	public static void handleParams(PreparedStatement ps,Object[] params) {
		if(params!=null&&params.length>0) {
			try {
				for(int i=0;i<params.length;i++) {
					ps.setObject(i+1, params[i]);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
 }
