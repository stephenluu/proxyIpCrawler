package com.proxyipcrawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {

	/**
	 * 写字节数组
	 * @param b
	 * @param string
	 * @return
	 */
	public static String writeByte(byte[] b, String string){
		
		string = encode(string);
		
		int p = string.lastIndexOf('/');
		String folder = string.substring(0, p);
		//String filename = string.substring(p+1);
		
		//System.out.println("路径："+string);
		File f = new File(folder);
		f.mkdirs();
		
		File file = new File(string);
		
		OutputStream os = null;
		
		try {
			os = new FileOutputStream(file);
			os.write(b);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return string;
	}
	
	
	
	/**
	 * 写字符串文件
	 * @param content
	 * @param path
	 * @return
	 */
	public static String writeByString(String content,String path){
		
		File file = new File(path); 
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (os == null)  return null;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)); //一层一层装饰
		
		try {
			
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return path;
	}

	/** 
     * 把中文转成Unicode码 
     * @param str 
     * @return 
     */  
    public static String chinaToUnicode(String str){  
        String result="";  
        for (int i = 0; i < str.length(); i++){  
            int chr1 = (char) str.charAt(i);  
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
                result+="\\u" + Integer.toHexString(chr1);  
            }else{  
                result+=str.charAt(i);  
            }  
        }  
        return result;  
    }  
    
    /**
     * 全部特殊字符转为“_”
     * @param string
     * @return
     */
	private static String encode(String string) {
		
		if (string == null)
			return null;
		
		string = string.replaceAll("[?]","_");
		string = string.replaceAll("=","_");
		string = string.replaceAll("&","_");
		string = string.replaceAll("#","_");
		return string;
	}
}
