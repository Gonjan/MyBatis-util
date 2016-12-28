package cn.com.zwan.mybatis;

public class ColumnInfo {
	private String columnName;
	private String javaName;
	private String javaType;
	private String jdbcType;
	private String parameterType;
	private boolean ispk;
	private String javaAndJdbcType;
	private String pageJavaAndJdbcType;
	private String javaParam;
	private String getter;
	private String setter;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getJavaName() {
		return javaName;
	}
	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public boolean isIspk() {
		return ispk;
	}
	public void setIspk(boolean ispk) {
		this.ispk = ispk;
	}
	public String getJavaAndJdbcType() {
		return javaAndJdbcType;
	}
	public void setJavaAndJdbcType(String javaAndJdbcType) {
		this.javaAndJdbcType = javaAndJdbcType;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getSetter() {
		return setter;
	}
	public void setSetter(String setter) {
		this.setter = setter;
	}
	public String getPageJavaAndJdbcType() {
		return pageJavaAndJdbcType;
	}
	public void setPageJavaAndJdbcType(String pageJavaAndJdbcType) {
		this.pageJavaAndJdbcType = pageJavaAndJdbcType;
	}
	public String getJavaParam() {
		return javaParam;
	}
	public void setJavaParam(String javaParam) {
		this.javaParam = javaParam;
	}
}
