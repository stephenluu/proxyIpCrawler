package com.proxyipcrawler.client;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.proxyipcrawler.util.FileUtils;
import com.proxyipcrawler.util.HtmlParserUtils;
import com.proxyipcrawler.util.ProxyValidater;

public class CrawlerClient {
	
	private static final String format = "http://www.xici.net.co/%s/%s";
	private static final String folder = "c:/proxyIp/";//存放ip文件夹
	
	static {
		File f = new File(folder);
		if(!f.exists()){ 
			f.mkdirs();
		}
		System.out.println("输出文件夹："+folder);
		System.out.println("每检验100个Ip保存一次，打印有效代理Ip，抓12页，大概10分钟左右");
	}
	
	public static void main(String[] args)  {

		String[]  types = {"nn","nt","wn","wt"};
		
		String fileName = null;
		for (String type : types) {
			for (int i = 1; i < 4; i++) {
				String str= String.format(format, type,i);
				
				if (type.equals("nn"))
					fileName = "国内高匿-" + i;
				else if (type.equals("nt"))
					fileName = "国内普通-" + i;
				else if (type.equals("wn"))
					fileName = "国外高匿-" + i;
				else if (type.equals("wt"))
					fileName = "国外普通-" + i;
				
				try {
					crawl(str,folder+fileName+".txt");
					System.out.println(folder+fileName+".txt");
				} catch (ParserException e) {
					System.err.println("网络错误。请重启.....");
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void crawl(String url,String path) throws ParserException{
		
		Parser parser = new Parser(url);
		
		NodeList tables = parser.extractAllNodesThatMatch(new TagNameFilter("table"));

		Node tr = null;
		Node table = tables.elementAt(0);
		NodeList trs = table.getChildren();
		
		String port ;
		String host;
		Set<String> set = new HashSet<String>();
		//去掉table head ,从3开始
		for (int i = 3,length = trs.size(); i < length; i++) {
			tr = trs.elementAt(i);
			
			if (tr instanceof TableRow) {
				NodeList children = tr.getChildren();
				host = HtmlParserUtils.getFirstText(children.elementAt(3));
				port = HtmlParserUtils.getFirstText(children.elementAt(5));
				String ip = host + ":" + port;
				
				if (!set.contains(ip)  && ProxyValidater.check(host, port)){
					System.out.println(ip);
					set.add(ip);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (String ip : set) {
			sb.append(ip+"\n");
		}
		String content = sb.toString();
		
		FileUtils.writeByString(content, path);
		
		System.out.println("有效代理总数："+set.size());
		
	}

}
