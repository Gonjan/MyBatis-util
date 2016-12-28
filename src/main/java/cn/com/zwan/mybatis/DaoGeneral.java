package cn.com.zwan.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwan.utils.FreeMarkerUtil;

public class DaoGeneral {
	//生成
		public void generalDaoModel(CodeInfo ci,List<ColumnInfo> lstCols)
		{
			try {
				//File file=new File(this.getClass().getClassLoader().getResource("dao.ftl").toURI());
				String templetePath=null;//file.getParent();
				String templeteName="dao.ftl";//file.getName();
				String daopath=ci.getSrcpath()+ci.getDaopath().replaceAll("\\.", "\\/");
				File fmpath=new File(daopath);
				fmpath.mkdirs();
				String modelname=CodeGeneralUtil.getTbModelName(ci.getTablename());
				String targetFile=daopath+File.separator+modelname+"Dao.java";
				File taf=new File(targetFile);
				if(!taf.exists())
				{//生成的文件不存在时，则生成，存在时则不需要生成了
					List<String> imports=getImport(ci);
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("package", ci.getDaopath());
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
			retu.add(ci.getBasedao());
			retu.add(ci.getModelpath()+"."+CodeGeneralUtil.getTbModelName(ci.getTablename()));
			return retu;
		}
}
