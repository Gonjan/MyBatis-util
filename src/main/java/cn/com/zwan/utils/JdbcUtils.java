package cn.com.zwan.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwan.mybatis.ColumnInfo;

public class JdbcUtils
{

	// 表示定义数据库的用户名
	private String USERNAME = "root";
	// 定义数据库的密码
	private String PASSWORD = "1234";
	// 定义数据库的驱动信息
	private String DRIVER = "com.mysql.jdbc.Driver";
	// 定义访问数据库的地址
	private String URL = "jdbc:mysql://localhost:3306/zwan_km";
	// 定义数据库的链接
	private Connection connection;
	// 定义sql语句的执行对象
	//private PreparedStatement pstmt;
	// 定义查询返回的结果集合
	//private ResultSet resultSet;

	public JdbcUtils(String driver,String url,String username,String password)
	{
		USERNAME=username;
		PASSWORD=password;
		DRIVER=driver;
		URL=url;
	}

	// 定义获得数据库的链接
	public Connection getConnection() throws Exception
	{
		try
		{
			Class.forName(DRIVER);
			System.out.println("注册驱动成功!!");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("数据库连接成功");
		}
		catch (Exception e)
		{
			throw e;
		}
		return connection;
	}

	public static List<ColumnInfo> getDbcolumn(Connection con,String m_TableName)
	{
		List<ColumnInfo> lst=new ArrayList<ColumnInfo>();
		try{
			DatabaseMetaData dbmd = con.getMetaData(); 
			//String schema=null;
			//    	ResultSet rs = dbmd.getColumns(con.getCatalog(), schema, tableName, null); 
			//    	rs.getString(DATA_TYPE) java.sql.Types 的 SQL 类型 
			//    	rs.getString(COLUMN_SIZE) 列的大小。对于 char 或 date 类型，列的大小是最大字符数，对于 numeric 和 decimal 类型，列的大小就是精度。 
			//    	rs.getString(DECIMAL_DIGITS) 小数部分的位数

			String columnName; 
			String columnType; 
			ResultSet colRet = dbmd.getColumns(null,"%", m_TableName,"%");
			boolean haveTable = false;
			while(colRet.next()) { 
				haveTable = true;
				columnName = colRet.getString("COLUMN_NAME"); 
				columnType = colRet.getString("TYPE_NAME"); 
				int datasize = colRet.getInt("COLUMN_SIZE"); 
				int digits = colRet.getInt("DECIMAL_DIGITS"); 
				int nullable = colRet.getInt("NULLABLE"); 
				System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ 
						nullable); 
				ColumnInfo ci=new ColumnInfo();
				ci.setColumnName(columnName);
				ci.setJavaName(getJavaName(columnName));
				ci.setJavaType(getJavaType(columnType));
				ci.setGetter(getGetter(ci.getJavaName()));
				ci.setSetter(getSetter(ci.getJavaName()));
				ci.setJdbcType(getJdbcType(columnType));
				ci.setJavaAndJdbcType("#{"+ci.getJavaName()+",jdbcType="+ci.getJdbcType()+"}");
				if("String".equals(ci.getJavaType()))
				{
					ci.setPageJavaAndJdbcType("${params."+ci.getJavaName()+"}");
					ci.setJavaParam("${"+ci.getJavaName()+"}");
				}else
				{
					ci.setPageJavaAndJdbcType("=#{params."+ci.getJavaName()+",jdbcType="+ci.getJdbcType()+"}");	
					ci.setJavaParam("=#{"+ci.getJavaName()+",jdbcType="+ci.getJdbcType()+"}");
				}
				
				ci.setParameterType(getParameterType(ci.getJdbcType()));
				ci.setIspk(false);
				lst.add(ci);
			}
			if(!haveTable) {
				System.err.println("没有表：" + m_TableName);
				System.exit(1);
			}
		
			ResultSet pkRSet = dbmd.getPrimaryKeys(null, null, m_TableName); 
			while( pkRSet.next() ) { 
				for(ColumnInfo ci:lst)
				{
					if(pkRSet.getObject(4).equals(ci.getColumnName()))
					{
						ci.setIspk(true);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return lst;
	}
	
	private static String getJavaName(String columnName)
	{
		String tmp=columnName.toLowerCase();
		String[] tmps=tmp.split("_");
		if(tmps.length>1)
		{
			StringBuffer sbuf=new StringBuffer();
			sbuf.append(tmps[0]);
			for (int i = 1; i < tmps.length; i++) {
				String cn=tmps[i];
				String ocn=cn.substring(0,1).toUpperCase()+cn.substring(1);
				sbuf.append(ocn);
			}
			return sbuf.toString();
		}else
		{
			return tmp;
		}
	}
	private static String getJdbcType(String columnType)
	{
		if("INT".equals(columnType))
		{
			return "INTEGER";
		}
		if("VARCHAR2".equals(columnType))
		{
			return "VARCHAR";
		}
		if("NUMBER".equals(columnType))   //这个地方还有待商榷，为什么oracle不能用NUMBER
		{
			return "NUMERIC";
		}
		return columnType;
	}
	private static String getParameterType(String jdbcType)
	{
		if("BIGINT".equals(jdbcType))
		{
			return "java.lang.Long";
		}else if("DECIMAL".equals(jdbcType))
		{
			return "java.math.BigDecimal";
		}
		return "java.lang.String";
	}
	private static String getJavaType(String columnType)
	{
		if("BIGINT".equals(columnType))
		{
			return "Long";
		}else if("DECIMAL".equals(columnType))
		{
			return "BigDecimal";
		}else if("TIMESTAMP".equals(columnType))
		{
			return "Date";
		}else if("INT".equals(columnType) || "NUMBER".equals(columnType))
		{
			return "Integer";
		}else if("VARCHAR".equals(columnType) || "VARCHAR2".equals(columnType))
		{
			return "String";
		}
		return "String";
	}
	private static String getGetter(String javaName)
	{
		return "get"+javaName.substring(0,1).toUpperCase()+javaName.substring(1);
	}
	private static String getSetter(String javaName)
	{
		return "set"+javaName.substring(0,1).toUpperCase()+javaName.substring(1);
	}
	public static void main(String[] args) {
		System.out.println(getJavaName("COM_TOM_KK_abc"));
	}

}

