package com.xiaozhi.orm.entitys;

import java.sql.*; 
import java.util.*; 
 
public class Dept {
	private String deptName;
	private Integer deptid;
	private Integer id;


	 public void setDeptName(String deptName){
		this.deptName=deptName;
	}
	 public void setDeptid(Integer deptid){
		this.deptid=deptid;
	}
	 public void setId(Integer id){
		this.id=id;
	}
	 public String getDeptName(){
		 return deptName;
	}
	 public Integer getDeptid(){
		 return deptid;
	}
	 public Integer getId(){
		 return id;
	}
}
