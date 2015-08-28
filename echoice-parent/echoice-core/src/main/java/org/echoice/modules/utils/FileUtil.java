package org.echoice.modules.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class FileUtil {
	public static void fileDown(String fileName,HttpServletResponse response) throws UnsupportedEncodingException{
		File file=new File(fileName);
		if(file.exists()){
			response.setContentType("application/stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(file.getName(),"UTF-8")+ "\"");
			try {
				OutputStream os = response.getOutputStream();
				BufferedOutputStream osbf=new BufferedOutputStream(os);				
				FileInputStream fis = new java.io.FileInputStream(file);
				BufferedInputStream fisBf=new BufferedInputStream(fis);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = fisBf.read(b)) > 0) {
					osbf.write(b, 0, i);					
				}
				fisBf.close();
				fis.close();
				osbf.flush();
				osbf.close();
				os.flush();
				os.close();
			} catch (Exception e) {
			}
		}
	}
}
