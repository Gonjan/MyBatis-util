package cn.com.zwan.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	private XPath xpath =null;
	private Document doc= null;
	private DocumentBuilder builder =null;
	
	public XmlParser(File file) throws Exception
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); // never forget this!
		builder = domFactory.newDocumentBuilder();
		doc= builder.parse(file);
		xpath = XPathFactory.newInstance().newXPath();
	}
	public List<String> getPathValue(String path) throws Exception
	{
		List<String> result=new ArrayList<String>();
		XPathExpression exlpath= xpath.compile(path);
		NodeList exlnodeList=(NodeList)exlpath.evaluate(doc, XPathConstants.NODESET);
		if(exlnodeList!=null)
		{
			int nlen=exlnodeList.getLength();
			if(nlen>0)
			{
				for (int i = 0; i < nlen; i++) {
					Node node=exlnodeList.item(i);
					if(node!=null)
					{
						Node tmp=node.getFirstChild();
						if(tmp!=null)
						{
							result.add(tmp.getNodeValue());
						}
					}
				}
			}
		}
		return result;
	}
//	private String getNodeValue(Node node,String nodename) throws Exception
//	{
//		XPathExpression exlpath= xpath.compile(nodename);//xpath.compile("folder");
//		Object exlresult = exlpath.evaluate(node, XPathConstants.NODESET);
//		NodeList exlnodeList=(NodeList)exlresult;
//		if(exlnodeList!=null)
//		{
//			Node exlnd=exlnodeList.item(0);
//			if(exlnd.getFirstChild()!=null)
//			{
//				//NamedNodeMap  exlnnmap=exlnd.getAttributes();
//				//String alias=exlnnmap.getNamedItem("alias").getNodeValue();
//				//String sqlval=exlnd.getFirstChild().getNodeValue().trim();
//				return exlnd.getFirstChild().getNodeValue().trim();
//			}
//		}
//		return null;
//	}
}
