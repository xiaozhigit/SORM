package com.xiaozhi.orm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.xiaozhi.orm.bean.Configuration;
import com.xiaozhi.orm.pool.DBConnPool;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 * @author xz
 *
 */
public class DBManager {
	private static Configuration conf;
	private static DBConnPool pool;
	static {//静态代码块
		Properties pros=new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		conf=new Configuration();
		conf.setDriver(pros.getProperty("driver"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setPwd(pros.getProperty("pwd"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUser(pros.getProperty("user"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setQueryClass(pros.getProperty("queryClass"));
		conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
		conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));

	}
	/**
	 * 创建数据库连接获得connection对象
	 * @return
	 */
	public static Connection createConn() {
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPwd());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获得connection对象
	 * @return
	 */
	public static Connection getConn() {
		if(pool==null) {
			pool=new DBConnPool();
		}
		return pool.getConnection();
	}
	/**
	 * 关闭传入的Result，Preparement,Connection
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void close( Connection conn,Statement ps,ResultSet rs) {

		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				pool.close(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn,Statement ps) {
		try {
			if (conn != null) {
				pool.close(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void close(Connection conn) {

		
		try {
			if (conn != null) {
				pool.close(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @return
	 */
	public static Configuration getConf() {
		return conf;
	}

}
