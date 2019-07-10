package com.demo;

import java.util.List;

import com.demo.entitys.Dept;
import com.demo.entitys.User;
import com.xiaozhi.orm.core.Query;
import com.xiaozhi.orm.core.QueryFactory;
import com.xiaozhi.orm.core.TableContext;

/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年7月9日 下午10:10:18 
* 类说明 

*/
public class QueryDemo {

	public QueryDemo() {
	}
	public static Query q=QueryFactory.createQuery();
	
	public static void  main(String[] args) {
		/**
		 *1. 第一步，调用下面方法根据数据库中的表生成实体类
		 */
	//	TableContext.updateJavaPOFile();
		/**
		 * 2.生成实体类后既可以调用相关接口进行相关操作了
		 * 为了避免多次生成可将生成实体类的代码在使用后删除或注释
		 */
		 //add();
		//delete();	
		//delete2();
		//update();
		//query();
		 //queryUnique();
		//queryValue();
		//queryNumber();
		//queryObject();
		queryByPage();
	}
	/**
	 * 添加方法
	 */
	public static void add() {
		User u=new User();
		u.setDeptid(12);
		u.setUsername("张三");
		u.setPassword("110");
		q.insert(u);
	}
	/**
	 * 删除方法1,删除时以对象的主键值为准
	 */
	public static void delete() {
		User u=new User();
		u.setId(3);
		u.setDeptid(12);
		u.setUsername("张三");
		u.setPassword("110");
		q.delete(u);
	}
	/**
	 * 删除方法2,删除时以对象的主键值为准
	 * 传入删除的实体类class和主键值
	 */
	public static void delete2() {
		q.delete(User.class,1);
	}
	/**
	 * 更改方法,修改数据值
	 * 
	 */
	public static void update() {
		//将要修改的属性值封装到实体类中并，传入要修改的字段名称,实体类的主键值必须设置，否则修改无效
		User u=new User();
		u.setId(2);
		u.setUsername("小王2");
		u.setPassword("111233");
		q.update(u,new String[] {"username","password"});
	}
	/**
	 * 查询方法,多个对象查询
	 * 传入sql语句，查询对象class，和参数，无参数传入空对象
	 * @return 返回查询对象集合
	 */
	public static void query() {
		String sql="select * from user";
		List<User> users=(List<User>)q.queryRows(sql, User.class, new Object[] {});
		for(User u:users) {
			System.out.println(u.getId()+" "+u.getUsername()+""+u.getPassword()+""+u.getDeptid());
		}
		
	}
	/**
	 * 查询方法,单个对象查询
	 * 传入sql语句，查询对象class，和参数，无参数传入空对象
	 * @return 返回查询对象
	 */
	public static void queryUnique() {
		String sql="select * from user where id=?";
		User u=(User)q.queryUniQueRow(sql, User.class,new Object[] {2});
			System.out.println(u.getId()+" "+u.getUsername()+""+u.getPassword()+""+u.getDeptid());
	}
	/**
	 * 查询对象的单个值
	 * 传入sql语句和参数，无参数传入空对象
	 * @return 返回查询单个值
	 */
	public static void queryValue() {
		String sql="select username from user where id=?";
		System.out.println(q.queryValue(sql, new Object[] {5}));
	}
	/**
	 * 查询单个指，返回类型为number
	 * 传入sql语句和参数，无参数传入空对象
	 * @return 返回Number
	 */
	public static void queryNumber() {
		String sql="select count(1) from user";
		System.out.print(q.queryNumber(sql, new Object[] {}));
	}
	/**
	 * 通过对象class及主键值查询对象
	 * @return 返回分页数据集合
	 */
	public static void queryObject() {
		User u=(User)q.queryById(User.class, 2);
		System.out.println(u.getId()+" "+u.getUsername()+""+u.getPassword()+""+u.getDeptid());
	}
	/**
	 * 查询分页
	 * @return 返回分页数据集合
	 */
	public static void queryByPage() {
		List<Dept> us=(List<Dept>)q.queryPage(Dept.class, 1, 4);
		for(Dept u:us) {
			System.out.println(u.getId()+" "+u.getDeptName()+""+u.getDeptid());
		}
		
	}




}
