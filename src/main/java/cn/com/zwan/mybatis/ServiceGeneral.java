package cn.com.zwan.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwan.utils.FreeMarkerUtil;

public class ServiceGeneral {
	//生成
		public void generalServiceModel(CodeInfo ci,List<ColumnInfo> lstCols)
		{
			try {
				//File file=new File(this.getClass().getClassLoader().getResource("service.ftl").toURI());
				String templetePath=null;//file.getParent();
				String templeteName="service.ftl";//file.getName();
				String servicepath=ci.getSrcpath()+ci.getServicepath().replaceAll("\\.", "\\/");
				File fmpath=new File(servicepath);
				fmpath.mkdirs();
				String modelname=CodeGeneralUtil.getTbModelName(ci.getTablename());
				String targetFile=servicepath+File.separator+modelname+"Service.java";
				File taf=new File(targetFile);
				if(!taf.exists())
				{//生成的文件不存在时，则生成，存在时则不需要生成了
					List<String> imports=getImport(ci);
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("package", ci.getServicepath());
					params.put("imports", imports);
					params.put("class", modelname);
					FreeMarkerUtil.analysisTemplate(templetePath,templeteName,targetFile,params);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private List<String> getImport(CodeInfo ci)
		{
			List<String> retu=new ArrayList<String>();
			retu.add(ci.getBaseservice());
			retu.add(ci.getModelpath()+"."+CodeGeneralUtil.getTbModelName(ci.getTablename()));
			retu.add(ci.getDaopath()+"."+CodeGeneralUtil.getTbModelName(ci.getTablename())+"Dao");
			return retu;
		}
}
