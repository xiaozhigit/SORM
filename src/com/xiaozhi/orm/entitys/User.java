package com.xiaozhi.orm.entitys;

import java.sql.*; 
import java.util.*; 
 
public class User {
	private String password;
	private Integer deptid;
	private Integer id;
	private String username;


	 public void setPassword(String password){
		this.password=password;
	}
	 public void setDeptid(Integer deptid){
		this.deptid=deptid;
	}
	 public void setId(Integer id){
		this.id=id;
	}
	 public void setUsername(String username){
		this.username=username;
	}
	 public String getPassword(){
		 return password;
	}
	 public Integer getDeptid(){
		 return deptid;
	}
	 public Integer getId(){
		 return id;
	}
	 public String getUsername(){
		 return username;
	}
}
