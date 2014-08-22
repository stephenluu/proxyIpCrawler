package com.proxyipcrawler.util;

import org.htmlparser.Node;

public class HtmlParserUtils {

	/**
	 * 第一个孩子的文本
	 * @param node
	 * @return
	 */
	public static String getFirstText(Node node){
		return node.getChildren().elementAt(0).getText();
	}
}
