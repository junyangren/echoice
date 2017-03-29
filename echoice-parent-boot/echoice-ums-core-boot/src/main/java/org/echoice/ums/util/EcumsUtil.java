package org.echoice.ums.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.echoice.ums.domain.EcObjects;

public class EcumsUtil {
	public static boolean isShow(HttpServletRequest request,String objAlias){
		
		List<EcObjects> list=(List<EcObjects>)request.getSession().getAttribute("permissionObjectList");
		for (EcObjects ecObjects : list) {
			if(objAlias.equalsIgnoreCase(ecObjects.getAlias())){
				return true;
			}
		}
		return false;
	}
	
	public static void renderText(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(content);
    }
	
	public static void renderHtml(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(content);
    }
	
    public static void renderJSON(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(content);
    }
    
    public static void renderJS(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript;charset=UTF-8");
        response.getWriter().write(content);
    }
}
