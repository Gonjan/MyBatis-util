package cn.com.zwan.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

//省略包的导入
public class FreeMarkerUtil{
	//templatePath模板文件存放路径
	//templateName 模板文件名称
	//filename 生成的文件名称
	public static void analysisTemplate(String templatePath,String templateName,String fileName,Map<?,?>root){
		try {
			Configuration config=new Configuration();
			//设置要解析的模板所在的目录，并加载模板文件
			//config.setDirectoryForTemplateLoading(new File(templatePath));
			config.setClassForTemplateLoading(FreeMarkerUtil.class, "/ftl"); 
			//设置包装器，并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper());

			//获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			//否则会出现乱码
			Template template=config.getTemplate(templateName,"UTF-8");
			//合并数据模型与模板
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos,"UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//		User user=new User();
		//		user.setUserName("name");
		//		user.setUserPassword("password");
		//		Map<String, Object> params=new HashMap<String, Object>();
		//		params.put("user", user);
		//		String templePath="F:\\workbench\\zwan\\MyBatisUtil\\src\\main\\java\\cn\\com\\zwan\\utils\\";
		//		String templateName="user.ftl";
		//		analysisTemplate(templePath,templateName,templePath+"aa.xml",params);

	}

	public static void generalXML(String targetFile,Map<String, Object> params)
	{
		try {
			File file=new File(FreeMarkerUtil.class.getClassLoader().getResource("xml.ftl").toURI());
			System.out.println(file.getParent()+","+file.getName());
			String templetePath=file.getParent();
			String templeteName=file.getName();
			analysisTemplate(templetePath,templeteName,targetFile,params);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public void inputstreamtofile(InputStream ins,File file) throws Exception{
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}
}

