package com.xiaozhi.orm.bean;
/**
 * 管理配置文件信息
 * @author xz
 *
 */
public class Configuration {
	/**
	 * 驱动类
	 */
	private String driver;
	/**
	 * 连接url
	 */
	private String url;
	/**
	 * 连接用户名
	 */
	private String user;
	/**
	 * 链接密码
	 */
	private String pwd;
	/**
	 * 链接数据库
	 */
	private String usingDB;
	/**
	 * 项目源码路径
	 */
	private String srcPath;
	/**
	 * 扫描生成额java实体路径
	 */
	private String poPackage;
	/**
	 * 项目使用的查询类路径
	 */
	private String queryClass;
	
	/**
	 * 连接池中最小链接数
	 */
	private int poolMinSize;
	/**
	 * 链接池中最大的链接数
	 */
	private int poolMaxSize;
	
	public String getQueryClass() {
		return queryClass;
	}
	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}
	
	
	public int getPoolMinSize() {
		return poolMinSize;
	}
	public void setPoolMinSize(int poolMinSize) {
		this.poolMinSize = poolMinSize;
	}
	public int getPoolMaxSize() {
		return poolMaxSize;
	}
	public void setPoolMaxSize(int poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getPoPackage() {
		return poPackage;
	}
	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}
	

}
