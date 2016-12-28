package cn.com.zwan.mybatis;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import cn.com.zwan.utils.JdbcUtils;
import cn.com.zwan.utils.XmlParser;

public class CodeGeneralUtil {

	public static void generalCode(String configxml) throws Exception
	{
		File file=new File(configxml);
		if(!file.exists())
		{
			throw new Exception("配置文件不存在！");
		}
		XmlParser xmlParser=new XmlParser(file);
		Connection conn=getConnection(xmlParser);//根据配置文件获得数据库连接
		CodeInfo ci=getCodeInfo(xmlParser);//获得生成代码的配置路径
		List<String> tables=getTables(xmlParser);//数据库表名
		if(tables!=null&&tables.size()>0)
		{
			for(String tb:tables)
			{
				String table=tb.toUpperCase();
				System.out.println(">>>>>>"+ table);
				ci.setTablename(table);
				List<ColumnInfo> lstCols=JdbcUtils.getDbcolumn(conn, table);//Dbcolumn(xmlParser);
				MapperGeneral mapperGeneral=new MapperGeneral();
				mapperGeneral.generalMapper(ci, lstCols);

				ModelGeneral mg=new ModelGeneral();
				mg.generalJavaModel(ci, lstCols);

				DaoGeneral dg=new DaoGeneral();
				dg.generalDaoModel(ci, lstCols);

				ServiceGeneral sg=new ServiceGeneral();
				sg.generalServiceModel(ci, lstCols);

				ServiceImplGeneral sig=new ServiceImplGeneral();
				sig.generalServiceModel(ci, lstCols);
			}
		}
	}

	//获得数据库连接
	private static Connection getConnection(XmlParser xmlParser) throws Exception
	{
		String driver=null;
		List<String> lst=xmlParser.getPathValue("/root/dbconnect/driver");
		if(lst!=null&&lst.size()==1)
		{
			driver=lst.get(0);
		}
		String url=null;
		lst=xmlParser.getPathValue("/root/dbconnect/url");
		if(lst!=null&&lst.size()==1)
		{
			url=lst.get(0);
		}
		String username=null;
		lst=xmlParser.getPathValue("/root/dbconnect/username");
		if(lst!=null&&lst.size()==1)
		{
			username=lst.get(0);
		}
		String password=null;
		lst=xmlParser.getPathValue("/root/dbconnect/password");
		if(lst!=null&&lst.size()==1)
		{
			password=lst.get(0);
		}
		if(driver!=null&&url!=null&&username!=null&&password!=null)
		{
			JdbcUtils jdbcUtils=new JdbcUtils(driver, url, username, password);
			return jdbcUtils.getConnection();
		}else
		{
			throw new Exception("请确定数据库连接信息正确！");
		}
	}
//	private static List<ColumnInfo> getDbcolumn(XmlParser xmlParser) throws Exception
//	{
//		String driver=null;
//		List<String> lst=xmlParser.getPathValue("/root/dbconnect/driver");
//		if(lst!=null&&lst.size()==1)
//		{
//			driver=lst.get(0);
//		}
//		String url=null;
//		lst=xmlParser.getPathValue("/root/dbconnect/url");
//		if(lst!=null&&lst.size()==1)
//		{
//			url=lst.get(0);
//		}
//		String username=null;
//		lst=xmlParser.getPathValue("/root/dbconnect/username");
//		if(lst!=null&&lst.size()==1)
//		{
//			username=lst.get(0);
//		}
//		String password=null;
//		lst=xmlParser.getPathValue("/root/dbconnect/password");
//		if(lst!=null&&lst.size()==1)
//		{
//			password=lst.get(0);
//		}
//		if(driver!=null&&url!=null&&username!=null&&password!=null)
//		{
//			JdbcUtils jdbcUtils=new JdbcUtils(driver, url, username, password);
//			return jdbcUtils.getDbcolumn();
//		}else
//		{
//			throw new Exception("请确定数据库连接信息正确！");
//		}
//	}

	private static CodeInfo getCodeInfo(XmlParser xmlParser) throws Exception
	{
		CodeInfo ci=new CodeInfo();
		ci.setSrcpath(xmlParser.getPathValue("/root/srcpath").get(0));
		ci.setDaopath(xmlParser.getPathValue("/root/daopath").get(0));
		ci.setMapperpath(xmlParser.getPathValue("/root/mapperpath").get(0));
		ci.setModelpath(xmlParser.getPathValue("/root/modelpath").get(0));
		ci.setServicepath(xmlParser.getPathValue("/root/servicepath").get(0));
		ci.setServiceimplpath(xmlParser.getPathValue("/root/serviceimplpath").get(0));
		ci.setBasedao(xmlParser.getPathValue("/root/basedao").get(0));
		ci.setBaseservice(xmlParser.getPathValue("/root/baseservice").get(0));
		ci.setBaseentitypath(xmlParser.getPathValue("/root/baseentityservice").get(0));
		return ci;
	}
	private static List<String> getTables(XmlParser xmlParser) throws Exception
	{
		return xmlParser.getPathValue("/root/tables/table");
	}

	//	public static void main(String[] args) {
	//		List<ColumnInfo> lst=JdbcUtils.getDbcolumn();
	//		CodeInfo ci=new CodeInfo();
	//		ci.setSrcpath("F:/workbench/zwan/MyBatisUtil/src/main/java/");
	//		ci.setTablename("KM_CATEGORY");
	//		ci.setDaopath("cn.com.zwan.km.dao");
	//		ci.setMapperpath("cn.com.zwan.km.model.xml");
	//		ci.setModelpath("cn.com.zwan.km.model");
	//		ci.setServicepath("cn.com.zwan.km.service");
	//		ci.setServiceimplpath("cn.com.zwan.km.service.impl");
	//		ci.setBasedao("cn.com.zwan.base.dao.BaseDao");
	//		ci.setBaseservice("cn.com.zwan.base.service.BaseService");
	//		//generalMapper(ci,lst);
	//		//generalJavaModel(ci,lst);
	//		
	////		MapperGeneral mapperGeneral=new MapperGeneral();
	////		mapperGeneral.generalMapper(ci, lst);
	////		
	////		ModelGeneral mg=new ModelGeneral();
	////		mg.generalJavaModel(ci, lst);
	//		
	////		DaoGeneral dg=new DaoGeneral();
	////		dg.generalDaoModel(ci, lst);
	//		
	////		ServiceGeneral sg=new ServiceGeneral();
	////		sg.generalServiceModel(ci, lst);
	//		
	//		ServiceImplGeneral sig=new ServiceImplGeneral();
	//		sig.generalServiceModel(ci, lst);
	//		
	//	}

	public static String getTbModelName(String tbname)
	{
		String tmp=tbname.toLowerCase();
		String[] tmps=tmp.split("_");
		if(tmps.length>1)
		{
			StringBuffer sbuf=new StringBuffer();
			sbuf.append(tmps[0].substring(0,1).toUpperCase()+tmps[0].substring(1));
			for (int i = 1; i < tmps.length; i++) {
				String cn=tmps[i];
				String ocn=cn.substring(0,1).toUpperCase()+cn.substring(1);
				sbuf.append(ocn);
			}
			return sbuf.toString();
		}else
		{
			String kk=tmp.substring(0,1).toUpperCase()+tmp.substring(1);
			return kk;
		}
	}

}

