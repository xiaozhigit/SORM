package com.xiaozhi.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年7月7日 上午1:23:04 
* 数据库交互回掉公共接口方法
*/
public interface CallBack {
	public Object doExecute(Connection conn,PreparedStatement ps,	ResultSet rs);
}
