package cn.com.zwan.mybatis;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwan.utils.FreeMarkerUtil;

public class MapperGeneral {
	//生成mapper文件
	public void generalMapper(CodeInfo ci,List<ColumnInfo> lstCols)
	{
		try {
			String modelname=CodeGeneralUtil.getTbModelName(ci.getTablename());

			String mapperpath=ci.getSrcpath()+ci.getMapperpath().replaceAll("\\.", "\\/");
			File fmpa=new File(mapperpath);
			fmpa.mkdirs();
			String targetFile=mapperpath+File.separator+modelname+"Mapper.xml";
			//File file=new File(this.getClass().getClassLoader().getResource("mapper.ftl").toURI());
			String templetePath=null;//file.getParent();
			String templeteName="mapper.ftl";//file.getName();

			Map<String, Object> params=new HashMap<String, Object>();
			ColumnInfo pkCol=null;//含有主键的表字段信息
			for(ColumnInfo coli:lstCols)
			{
				if(coli.isIspk())
				{
					pkCol=coli;
					break;
				}
			}
			params.put("tbname", ci.getTablename());//表名
			params.put("pkCol", pkCol);//主键字段
			params.put("cols", lstCols);//存储数据库表字段信息
			params.put("namespace", ci.getDaopath()+"."+modelname+"Dao");//命名空间
			params.put("type", ci.getModelpath()+"."+modelname);//type，即model的完整类路径
			params.put("basecolumn", getBaseColumn(lstCols));//basecolumn
			FreeMarkerUtil.analysisTemplate(templetePath,templeteName,targetFile,params);
			System.out.println("生成MAPPER文件成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getBaseColumn(List<ColumnInfo> lstCols)
	{
		StringBuffer sbuf=new StringBuffer();
		for(ColumnInfo col:lstCols)
		{
			sbuf.append(col.getColumnName()+",");
		}
		return sbuf.substring(0,sbuf.length()-1);
	}
}
