package cn.com.zwan.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwan.utils.FreeMarkerUtil;

public class ModelGeneral {
	//生成
		public void generalJavaModel(CodeInfo ci,List<ColumnInfo> lstCols)
		{
			try {
				//File file=new File(this.getClass().getClassLoader().getResource("model.ftl").toURI());
				
				List<ColumnInfo> nlstCols=new ArrayList<ColumnInfo>();
				for(ColumnInfo col:lstCols)
				{
					String getter=col.getGetter();
					if(getter.equals("getSid")||getter.equals("getStatus")||getter.equals("getCreatedAt")
							||getter.equals("getUpdatedAt")||getter.equals("getVersion"))
					{
						continue;
					}else
					{
						nlstCols.add(col);
					}
				}
				
				
				String templetePath=null;//file.getParent();
				String templeteName="model.ftl";//file.getName();
				String modelpath=ci.getSrcpath()+ci.getModelpath().replaceAll("\\.", "\\/");
				File fmpath=new File(modelpath);
				fmpath.mkdirs();
				String modelname=CodeGeneralUtil.getTbModelName(ci.getTablename());
				String targetFile=modelpath+File.separator+modelname+".java";
				List<String> imports=getImport(ci,nlstCols);
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("package", ci.getModelpath());
				params.put("imports", imports);
				params.put("class", modelname);
				params.put("cols", nlstCols);//存储数据库表字段信息
				FreeMarkerUtil.analysisTemplate(templetePath,templeteName,targetFile,params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private List<String> getImport(CodeInfo codeInfo,List<ColumnInfo> lstCols)
		{
			List<String> retu=new ArrayList<String>();
			for(ColumnInfo ci:lstCols)
			{
				String jty=ci.getJavaType();
				if("Date".equals(jty))
				{
					if(!retu.contains("java.util.Date"))
					{
						retu.add("java.util.Date");
					}
				}else if("BigDecimal".equals(jty))
				{
					if(!retu.contains("java.math.BigDecimal"))
					{
						retu.add("java.math.BigDecimal");
					}
				}
			}
			retu.add(codeInfo.getBaseentitypath());
			return retu;
		}

}
