package org.echoice.modules.web.controller;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.modules.web.paper.PageNavi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
/**
 * Spring MVC 基类
 * @author wujy
 *
 */
public abstract class SpringBaseController extends MultiActionController{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public abstract ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception;
    /**
     * 初始化binder
     * 设置日期Editor及允许Integer,Double的字符串为空
     */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(true));
    }
    
    /**
     * 回调函数，校验时声明对象的名字,默认为首字母小写的类名
     */
    protected String getCommandName(Object command) {
        return StringUtils.uncapitalize(StringUtils.substringAfterLast(command.getClass().getName(), "."));
    }
    
    /**
     * 绑定并校验对象。
     * 忽略绑定错误，而校验错误将在BindException中返回
     */
    protected BindException bindValidObject(HttpServletRequest request, Object command) throws Exception {
        //回调函数，供子类扩展
        preBind(request, command);
        //绑定
        ServletRequestDataBinder binder = createBinder(request, command);
        binder.bind(request);
        //校验
        BindException errors = new BindException(command, getCommandName(command));
        Validator[] validators = getValidators();
        if (validators != null) {
        	for (int i = 0; i < validators.length; i++) {
        		Validator validator=validators[i];
        		ValidationUtils.invokeValidator(validator, command, errors);
			}
        }
        return errors;
    }
    /**
     * 绑定对象。
     * 
     */
    protected void bindObject(HttpServletRequest request, Object command) throws Exception {
        //回调函数，供子类扩展
        preBind(request, command);
        //绑定
        ServletRequestDataBinder binder = createBinder(request, command);
        binder.bind(request);
    }
    
    /**
     * 回调函数，在BindObject的最开始调用,绑定一些不由Binder自动绑定的属性.
     * 通常是需要查询数据库来获得对象的绑定。
     */
    protected void preBind(HttpServletRequest request, Object object) throws Exception {
        //在子类实现
    }
    /**
     * 回填函数.校验出错时,将出错的对象及出错信息放回model.
     */
    protected ModelAndView onBindError(HttpServletRequest request, BindException errors, String viewName) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addAllObjects(errors.getModel());
        return mav;
    }
    /**
     * 直接向客户端返回字符串，不通过View页面渲染
     */
    protected void rendText(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer=response.getWriter();
        writer.write(content);
    }
    /**
     * 输出json格式，用于ajax请求
     * @param response
     * @param content
     * @throws IOException
     */
    protected void rendJson(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript;charset=UTF-8");
        Writer writer=response.getWriter();
        writer.write(content);
    }
    
    /**
     * 输出json格式，用于ajax请求
     * @param response
     * @param content
     * @throws IOException
     */
    protected void rendJS(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript;charset=UTF-8");
        Writer writer=response.getWriter();
        writer.write(content);
    }
    
    /**
     * 分页时获取当前页码
     * @param request
     * @return
     */
    protected int getPageNo(HttpServletRequest request){
		return getPageNo(request,null);
	}
    /**
     * 分页时获取当前页码
     * @param request
     * @param pagetNoTag
     * @return
     */
    protected int getPageNo(HttpServletRequest request,String pagetNoTag){
    	if(StringUtils.isBlank(pagetNoTag)){
    		pagetNoTag="pageNo";
    	}
		String pageNo=request.getParameter(pagetNoTag);
		if(StringUtils.isNotBlank(pageNo)){
			try{
				return (Integer.parseInt(pageNo));
			}catch(NumberFormatException ex){
				
			}
		}
		return 1;
	}
    
    /**
     * 分页时获取每页记录数
     * @param request
     * @return
     */
    protected int getPageSize(HttpServletRequest request){
		return getPageSize(request,null);		
	}
    /**
     * 分页时获取每页记录数
     * @param request
     * @param pageSizeTag
     * @return
     */
    protected int getPageSize(HttpServletRequest request,String pageSizeTag){
    	if(StringUtils.isBlank(pageSizeTag)){
    		pageSizeTag="pageSize";
    	}
		String pageSize=request.getParameter(pageSizeTag);
		if(StringUtils.isNotEmpty(pageSize)){
			int pageSizeInt=0;
			try{
				pageSizeInt=Integer.parseInt(pageSize);
			}catch(NumberFormatException ex){
				
			}
			if(pageSizeInt>PageBean.MAX_PAGE_SIZE){
				pageSizeInt= PageBean.MAX_PAGE_SIZE;
			}else if(pageSizeInt==0){
				pageSizeInt= PageBean.DEFAULT_PAGE_SIZE;
			}
			return pageSizeInt;
		}else{
			return PageBean.DEFAULT_PAGE_SIZE;
		}		
	}
    
    /**
     *设置分页导航及数据对象到 request（pageNavi，listData）
     * @param request
     * @param pageBean
     */
    protected void setPageNavi2Attr(HttpServletRequest request,PageBean pageBean){
    	String pageNavi=PageNavi.getPageNaviButton(request, pageBean);
    	request.setAttribute("pageNavi", pageNavi);
    	request.setAttribute("listData", pageBean.getDataList());
    }
}
