package cn.test.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class genFile {

	
	@Test
	public void genFile1() throws Exception {
		//1.直接创建一个Configuration对象
		Configuration configuration=new Configuration(Configuration.getVersion());
	    //设置模板文件所在的路径
		configuration.setDirectoryForTemplateLoading(new File("E:/e3/e3-item-web/src/main/webapp/WEB-INF"));
		//设置模板集所使用的字符编码
		configuration.setDefaultEncoding("UTF-8");
		//加载一个模板，创建一个模板对象
		Template template = configuration.getTemplate("1.flt");
	    //数据集
		Map dataModel=new HashMap<>();
		//向数据集中添加数据
		dataModel.put("hello", "aa");
		Writer out = new FileWriter(new File("D:/hello.html"));
	    //调用模板对象的process方法输出文件
		template.process(dataModel, out);
		out.close();
	
	}
}
