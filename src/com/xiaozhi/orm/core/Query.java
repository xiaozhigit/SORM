package com.xiaozhi.orm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiaozhi.orm.bean.ColumnInfo;
import com.xiaozhi.orm.bean.TableInfo;
import com.xiaozhi.orm.utils.JdbcUtil;
import com.xiaozhi.orm.utils.ReflectUtils;

/**
 * 负责查询(对外低通服务核心类)
 * @author xz
 *
 */
@SuppressWarnings("all")
public abstract class Query implements Cloneable{
	/**
	 * 采用模板方法将jdbc操作封装成模板，便于重用
	 * @param sql sql语句
	 * @param params sql参数
	 * @param clazz 记录封装到的bean类
	 * @param callBack CallBack实现类，实现 回调
	 * @return
	 */
	public Object executeQueryTemplate(String sql,Object[] params,Class clazz,CallBack callBack) {
		Connection conn=DBManager.getConn();
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sql);
			//给sql 设参
			JdbcUtil.handleParams(ps,params);
			rs=ps.executeQuery();
			return callBack.doExecute(conn, ps, rs);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			DBManager.close(conn,ps,rs);
		}
	}
	/**
	 * 直接执行sql语句，外部传入sql和参数可调用
	 * @param sql sql语句
	 * @param params  参数
	 * @return 变换行数
	 */
	public int executeDML(String sql,Object[] params) {
		Connection conn=DBManager.getConn();
		int count=0;
		try {
			PreparedStatement ps=null;
			ps=conn.prepareStatement(sql);
			JdbcUtil.handleParams(ps, params);
			System.out.print(sql);
			count=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn);
		}
		return count;
	};
	/**
	 * 将对象存入到数据库中，如果数字为null则设置为0
	 */
	public void insert(Object obj) {
		//obj==>表中 insert into 表名（id，namee，pwd）value(?,?,?)
		Class c=obj.getClass();
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		List<Object> params=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder("insert into "+tableInfo.getName()+" (");
		int countNotNullField=0;//统计不为空字段数量
		Field[] fs=c.getDeclaredFields();
		for(Field f:fs) {
			String fieldName=f.getName();
			Object fieldValue=ReflectUtils.invokeGet(fieldName, obj);
			if(fieldValue!=null) {
				countNotNullField++;
				sql.append(fieldName+",");
				params.add(fieldValue);
			}
		}
		sql.setCharAt(sql.length()-1,')');
		sql.append(" values (");
		for(int i=0;i<countNotNullField;i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1,')');
		executeDML(sql.toString(),params.toArray());
	}
	
	/**
	 * 通过传入id和对象类型删除数据
	 * @param clazz  删除对象
	 * @param id  id 对象主键值
	 * @return
	 */
	public int delete(Class clazz, Object id) {
		//Emp.calss,2==> delete from emp where id=2
		//通过Class对象找TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		ColumnInfo onlyPrikey = tableInfo.getOnlyPriKey();
		//构造sql语句
		String sql = "delete from " + tableInfo.getName() + " where " + onlyPrikey.getName() + "=?";
		return executeDML(sql, new Object[] { id });
	}

	/**
	 * 删除对象
	 * @param obj 删除的对象
	 */
	public void delete(Object obj) {
		Class c=obj.getClass();
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();//主键
		//通过反射机制调用set或get方法
		Object priKeyValue=ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
		delete(c,priKeyValue);
	}
	/**
	 * 更新对象对应的记录，只更新指定属性的值
	 * @return 返回记录变更的行数
	 * 
	 */
	public int update(Object obj, String[] fieldNames) {
		//obj {"uname","pwd"}==>update 表名 set uname=?,pwd=? where id=?
		Class c=obj.getClass();
		TableInfo tableInfo=TableContext.poClassTableMap.get(c);
		List<Object> params=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder("update "+tableInfo.getName()+" set ");
		ColumnInfo prikey=tableInfo.getOnlyPriKey();
		Field[] fs=c.getDeclaredFields();
		for(String fname:fieldNames) {
			Object fvalue=ReflectUtils.invokeGet(fname, obj);
			params.add(fvalue);
			sql.append(fname+"=?,");
		}
		sql.setCharAt(sql.length()-1,' ');
		sql.append(" where ");
		sql.append(prikey.getName()+"=? ");
		params.add(ReflectUtils.invokeGet(prikey.getName(), obj));
		
		return executeDML(sql.toString(),params.toArray());
	}

	/**
	 * 查询返回多行记录，并将每行记录封装到clazz指定的对象中
	 * @param slq 查询语句
	 * @param clazz 封装类的class对象
	 * @param params  参数
	 * @return
	 */
	public List queryRows(final String sql, final Class clazz, final Object[] params) {
		
		return (List)executeQueryTemplate(sql, params, clazz, new CallBack() {
			List list =null ;//存储查询结果
			@Override
			public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
				try {
					ResultSetMetaData metaData = rs.getMetaData();
					// 多行
					while (rs.next()) {
						if(list==null) {
							list=new ArrayList();
						}
						Object rowObject = clazz.newInstance();// 调用javabean的无参构造
						// 多列 select username,pwd,age, from user where id>? and age>18
						for (int i = 0; i < metaData.getColumnCount(); i++) {
							String columnName = metaData.getColumnLabel(i + 1);// username
							Object columnValue = rs.getObject(i + 1);
							// 调用rowObject对象的setUsername(String name)方法，将columnValue的值设置进去
							ReflectUtils.invokeSet(rowObject, columnName, columnValue);
						}
						list.add(rowObject);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
			}
		});

	};
	/**
	 * 查询返回一个数字（一行一列），并将指返回
	 * @param sql
	 * @param params
	 * @return
	 */
	
	public  Number queryNumber(String sql,Object[] params) {
		return (Number)queryValue(sql,params);
	};
	/**
	 * 查询单个对象并返回
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object queryUniQueRow(String sql,Class clazz, Object[] params) {
		List list=queryRows(sql,clazz,params);
		return (list==null||list.size()==0)?null:list.get(0);
	};
	
	/**
	 * 返回查询的单个值
	 */
	public Object queryValue(String sql,Object[] params) {
		return 	executeQueryTemplate(sql,params,null,new CallBack() {
				@Override
				public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
					Object value=null;
					try {
						while(rs.next()) {
							value=rs.getObject(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return value;
				}
			});
	}
	/**
	 * 分页查询
	 * @param indexNum 开始位置
	 * @param size 每页显示记录
	 * @return
	 */
	public  List queryPage(Class clazz,int indexNum,int size) {
		//select * from dept limit 2,2
		//通过Class对象找TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		//构造sql语句
		String sql = "select * from " + tableInfo.getName() + " limit ?,?";
		return queryRows(sql,clazz,new Object[] {indexNum,size});
	};
	
	/**
	 * 实现浅克隆
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	/**
	 * 根据对象主键值查询对象
	 * @param clazz
	 * @param id
	 * @return 对象
	 */
	public Object queryById(Class clazz,Object id) {
		//Emp.calss,2==> select *  from emp where id=2
		//通过Class对象找TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		ColumnInfo onlyPrikey = tableInfo.getOnlyPriKey();
		//构造sql语句
		String sql = "select *  from " + tableInfo.getName() + " where "+ onlyPrikey.getName() +"=?";
		return queryUniQueRow(sql, clazz,new Object[] {id});
	}

}
