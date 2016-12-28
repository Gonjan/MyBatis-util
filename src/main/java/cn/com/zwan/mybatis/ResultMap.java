package cn.com.zwan.mybatis;

import java.util.List;

public class ResultMap {
	private String namespace;
	private String type;
	private List<ColumnInfo> lstCols;
	private String baseColumn;
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ColumnInfo> getLstCols() {
		return lstCols;
	}
	public void setLstCols(List<ColumnInfo> lstCols) {
		this.lstCols = lstCols;
	}
	public String getBaseColumn() {
		return baseColumn;
	}
	public void setBaseColumn(String baseColumn) {
		this.baseColumn = baseColumn;
	}
}
