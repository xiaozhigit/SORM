package com.xiaozhi.orm.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.xiaozhi.orm.bean.ColumnInfo;
import com.xiaozhi.orm.bean.JavaFieldGetSet;
import com.xiaozhi.orm.bean.TableInfo;
import com.xiaozhi.orm.core.DBManager;
import com.xiaozhi.orm.core.TypeConvertor;

/** 
* @author 作者  xiaozhi 
* @version 创建时间：2019年5月12日 下午10:40:24 
* 类说明 

*/
public class JavaFileUtils {
	
	/**
	 * 根据字段信息生成Java属性信息 如：varchar username -->private String username;以及相应的set和get方法源码
	 * @param column 
	 * @param convertor
	 * @return
	 */
	public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column,TypeConvertor convertor) {
		JavaFieldGetSet jfgs=new JavaFieldGetSet();
		
		String javaFieldType=convertor.datebaseType2JavaType(column.getDataType());
		jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");
		//public  String getUsername(){ return username;}
		//生成get方法的源码
		StringBuilder getSrc=new StringBuilder();
		 getSrc.append("\t public "+javaFieldType+" get"+StringUtils.firstChar2UpperCase(column.getName())+"(){\n");
		 getSrc.append("\t\t return "+column.getName()+";\n");
		 getSrc.append("\t}\n");
		 jfgs.setGetInfo(getSrc.toString());
		 
	    //public  String setUsername(String username){ this.username=username;}
		//生成set方法的源码
		StringBuilder setSrc=new StringBuilder();
			setSrc.append("\t public void set"+StringUtils.firstChar2UpperCase(column.getName())+"(");
			setSrc.append(javaFieldType+" "+column.getName()+"){\n");
			setSrc.append("\t\tthis."+column.getName()+"="+column.getName()+";\n");
			setSrc.append("\t}\n");
		 jfgs.setSetInfo(setSrc.toString());
		 
		 return jfgs;
	}
	/**
	 * 根据表信息生成Java类的源代码
	 * @param tableInfo
	 * @param convertor
	 * @return Java类源代码
	 */
	public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor) {
		StringBuilder src=new StringBuilder();
		Map<String,ColumnInfo> columns=tableInfo.getColumns();
		List<JavaFieldGetSet> javaFields=new ArrayList<JavaFieldGetSet>();
		for(ColumnInfo c:columns.values()) {
			javaFields.add(createFieldGetSetSRC(c,convertor));
		}
		//生成package语句
		src.append("package "+DBManager.getConf().getPoPackage()+";\n\n");
		
		//生成import语句
		src.append("import java.sql.*; \n");
		src.append("import java.util.*; \n \n");
		//生成类声明语句
		src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getName()+" {\n"));
		//生成属性列表
		for(JavaFieldGetSet f:javaFields) {
			src.append(f.getFieldInfo());
		}
		src.append("\n\n");
		//生成get方法
		for(JavaFieldGetSet f:javaFields) {
			src.append(f.getSetInfo());
		}
		//生成set方法
		for(JavaFieldGetSet f:javaFields) {
			src.append(f.getGetInfo());
		}
		//生成类结束
		src.append("}\n");
		return src.toString();
	}
	public static void createJavaPOFile(TableInfo tableInfo,TypeConvertor convertor) {
		String src=createJavaSrc(tableInfo,convertor);
		String srcPath=DBManager.getConf().getSrcPath()+"\\";
		String packagePath=DBManager.getConf().getPoPackage().replaceAll("\\.","/");
		
		File f=new File(srcPath+packagePath);
		
		if(!f.exists()) {//如果路径不存在则创建路径
			f.mkdirs();
		}
		BufferedWriter bw=null;
		try {
			bw=new BufferedWriter(
					new FileWriter(f.getAbsoluteFile()+"/"+StringUtils.firstChar2UpperCase(tableInfo.getName())+".java"));
		   bw.write(src);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(bw!=null) {
					bw.close();
				}
			}catch(IOException e) {e.printStackTrace();}
		}
	}
}
