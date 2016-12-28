package cn.com.zwan.mybatis;

public class CodeInfo {
	private String srcpath;
	private String tablename;
	private String daopath;
	private String servicepath;
	private String modelpath;
	private String mapperpath;
	private String basedao;
	private String baseservice;
	private String serviceimplpath;
	private String baseentitypath;
	
	
	public String getDaopath() {
		return daopath;
	}
	public void setDaopath(String daopath) {
		this.daopath = daopath;
	}
	public String getServicepath() {
		return servicepath;
	}
	public void setServicepath(String servicepath) {
		this.servicepath = servicepath;
	}
	public String getModelpath() {
		return modelpath;
	}
	public void setModelpath(String modelpath) {
		this.modelpath = modelpath;
	}
	public String getMapperpath() {
		return mapperpath;
	}
	public void setMapperpath(String mapperpath) {
		this.mapperpath = mapperpath;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getSrcpath() {
		return srcpath;
	}
	public void setSrcpath(String srcpath) {
		this.srcpath = srcpath;
	}
	public String getBasedao() {
		return basedao;
	}
	public void setBasedao(String basedao) {
		this.basedao = basedao;
	}
	public String getServiceimplpath() {
		return serviceimplpath;
	}
	public void setServiceimplpath(String serviceimplpath) {
		this.serviceimplpath = serviceimplpath;
	}
	public String getBaseservice() {
		return baseservice;
	}
	public void setBaseservice(String baseservice) {
		this.baseservice = baseservice;
	}
	public String getBaseentitypath() {
		return baseentitypath;
	}
	public void setBaseentitypath(String baseentitypath) {
		this.baseentitypath = baseentitypath;
	}
	
}
