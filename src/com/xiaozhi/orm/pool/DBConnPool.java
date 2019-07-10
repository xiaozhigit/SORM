package com.xiaozhi.orm.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiaozhi.orm.core.DBManager;

/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年6月30日 下午4:05:36 
* 数据库连接线程池对象
*/
public class DBConnPool {
	/**
	 * 连接池对象
	 */
 private static List<Connection> pool;
 /**
  * 最大链接数
  */
 private static final int POOL_MAX_SIZE=DBManager.getConf().getPoolMaxSize();
 /**
  * 最小链接数
  */
 private static final int POOL_MIN_SIZE=DBManager.getConf().getPoolMinSize();
 /**
  * 初始化连接池
  */
 public void initPool() {
	 if(pool==null) {
		 pool=new ArrayList<Connection>();
	 }
	 while(pool.size()<POOL_MIN_SIZE) {
		 pool.add(DBManager.createConn());
	 }
 }
 /**
  * 从连接池中取出一个连接
  * @return
  */
 public  synchronized  Connection getConnection() {
	 int last_index=pool.size()-1;
	 Connection conn=pool.get(last_index);
	 pool.remove(last_index);
	 return conn;
 }
 /*
  * 将链接返回池中
  */
 public synchronized void close(Connection conn) {
	 if(pool.size()>=POOL_MAX_SIZE) {
		 try {
			if(conn!=null) {
				 conn.close();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }else {
		 pool.add(conn);
	 }
 }
 public DBConnPool() {
	 initPool();
 };
}
