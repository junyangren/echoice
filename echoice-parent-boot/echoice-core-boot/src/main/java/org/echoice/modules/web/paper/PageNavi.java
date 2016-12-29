package org.echoice.modules.web.paper;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @desc 
 * @author jun yang
 * @date 2007-1-5
 */
public class PageNavi {
	private static final int NUM_NEXT=3;
	private static final int NUM_PRE=2;
	/**
	 * 将参数设置到隐藏域
	 * @param request
	 * @param excludeArr
	 * @return
	 */
	private static StringBuffer getFormHiddenInput(ServletRequest request,String excludeArr[]){
		StringBuffer outputHiddenBf = new StringBuffer();
		Enumeration enumeration= request.getParameterNames();       
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = ParamUtil.getParameter(request,name);
            //保存查询参数和值
            boolean isContinue=false;
            for (int i = 0; i < excludeArr.length; i++) {
            	if (name.equals(excludeArr[i])) {
            		isContinue=true;
                    break;
                }
			}
            if(isContinue){
            	continue;
            }
            outputHiddenBf.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value +"\" />\n");
        }
        return outputHiddenBf;
	}
	
	private static String getPageNavi(HttpServletRequest request,PageBean page,String type){
		//输出分页参数表单
		StringBuffer output = new StringBuffer();
		String uriPath=request.getRequestURI();
		output.append("<form id=\"pagerform\" action=\""+uriPath+"\" method=\"post\" name=\"pagerform\">\n");
		StringBuffer paramBuf=getFormHiddenInput(request,new String []{"pageNo","pageSize"});
		paramBuf.append("<input type=\"hidden\" name=\"pageNo\" value=\"" + page.getCurrentPageNo() + "\"  />\n");
        paramBuf.append("<input type=\"hidden\" name=\"pageSize\" value=\"" + page.getPageSize() + "\"  />\n");
        String nextDisabled = "";
        String prevDisabled = "";
        String bar = "";
        if (!page.hasNextPage())
            nextDisabled = "disabled";
        if (page.getCurrentPageNo()<= 1)
            prevDisabled = "disabled";
        
        //---------------------按钮型的导航条-----------------------//
        if (type.equalsIgnoreCase("BUTTON")) {
            String pageSizeInput = "<input type=\"text\" style=\"width:24px;\" maxlength=\"2\" value=\"" + page.getPageSize() + "\" "
                                   + "onChange=\"javascript:this.form.pageSize.value=this.value;this.form.submit();\" />";
            String firstButton = "<input type=\"button\" value=\"首  页\" " + prevDisabled + " "
                                 +
                    "onClick=\"javascript:this.form.pageNo.value='1';this.form.submit();\" />";
            //
            String prevButton = "<input type=\"button\" value=\"上一页\" " + prevDisabled + " "
                                +
                    "onClick=\"javascript:this.form.pageNo.value='"+page.getPreviousPage()+"';this.form.submit();\" />";
            //
            String nextButton = "<input type=\"button\" value=\"下一页\" " + nextDisabled + " "
                                +
                    "onClick=\"javascript:this.form.pageNo.value='"+page.getNextPage()+"';this.form.submit();\" />";
            //
            String lastButton = "<input type=\"button\" value=\"末一页\" " + nextDisabled + " "
                                +
                    "onClick=\"javascript:this.form.pageNo.value='"+page.getTotalPageCount()+"';this.form.submit();\" />";
            //
            String pageNoInput = "<input type=\"text\" style=\"width:56px;\" value='" + page.getCurrentPageNo() + "' "
                                 + "onChange=\"javascript:this.form.pageNo.value=this.value\" />";

            bar = "每页pageSize条记录 | \n"
                  + "共pages页/total条记录 | \n"
                  + "first \n prev \n next \n last \n | 第pageNoTag页\n"
                  +
                    " <input type=\"submit\" value=\"go\" />\n";

            bar = StringUtils.replace(bar, "pageSize", pageSizeInput);
            bar = StringUtils.replace(bar, "pages", String.valueOf(page.getTotalPageCount()));
            bar = StringUtils.replace(bar, "total", String.valueOf(page.getTotalSize()));
            bar = StringUtils.replace(bar, "first", firstButton);
            bar = StringUtils.replace(bar, "prev", prevButton);
            bar = StringUtils.replace(bar, "next", nextButton);
            bar = StringUtils.replace(bar, "last", lastButton);
            bar = StringUtils.replace(bar, "pageNoTag", pageNoInput);
        }
        
        //-------------------------文字型----------------------------//
        if (type.equalsIgnoreCase("TEXT")) {
            String pageSizeInput = "<input type=\"text\" style=\"width:24px;\" maxlength=\"2\" value=\"" + page.getPageSize() + "\" "
                                   + "onChange=\"javascript:this.form.pageSize.value=this.value;this.form.submit();\" />";
            String firstText = "首  页";
            String prevText = "上一页";
            String nextText = "下一页";
            String lastText = "最后一页";
            if (prevDisabled.equalsIgnoreCase("")) {
                firstText = "<a href=\"javascript:void(0);\" "
                            +
                        "onClick=\"javascript:document.pagerform.pageNo.value='1';document.pagerform.submit();return false;\">"
                            + "首  页"
                            + "</a>";
                prevText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getPreviousPage()+"';document.pagerform.submit();return false;\">"
                           + "上一页"
                           + "</a>";
            }
            if (nextDisabled.equalsIgnoreCase("")) {
                nextText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getNextPage()+"';document.pagerform.submit();return false;\">"
                           + "下一页"
                           + "</a>";
                lastText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getTotalPageCount()+"';document.pagerform.submit();return false;\">"
                           + "末一页"
                           + "</a>";
            }
            String pageNoInput = "<input type=\"text\" style=\"width:56px;\" value='" + page.getCurrentPageNo() + "' "
                                 + "onChange=\"javascript:this.form.pageNo.value=this.value\">";

            bar = "每页pageSize条记录 | \n"
                  + "共pages页/total条记录 | \n"
                  + "first \n prev \n next \n last \n | 第pageNoTag页\n"
                  +
                    " <input type=\"submit\" class=\"button\" value=\"go\">\n";

            bar = StringUtils.replace(bar, "pageSize", pageSizeInput);
            bar = StringUtils.replace(bar, "pages", String.valueOf(page.getTotalPageCount()));
            bar = StringUtils.replace(bar, "total", String.valueOf(page.getTotalSize()));
            bar = StringUtils.replace(bar, "first", firstText);
            bar = StringUtils.replace(bar, "prev", prevText);
            bar = StringUtils.replace(bar, "next", nextText);
            bar = StringUtils.replace(bar, "last", lastText);
            bar = StringUtils.replace(bar, "pageNoTag", pageNoInput);
        }
        
        //---------------------按钮型的导航条-----------------------//
        if (type.equalsIgnoreCase("SIMPLEBUTTON")) {
            String pageSizeInput = "<input class=\"navbar\" type=\"text\" style=\"width:24px;\" maxlength=\"2\" value=\"" + page.getPageSize() +
                                   "\" "
                                   + "onChange=\"javascript:this.form.pageNo.value=this.value;this.form.submit();\" />";
            
            String prevButton = "<input class=\"navbar\" type=\"button\" value=\"上一页\" " + prevDisabled +
                                " "
                                +
                    "onClick=\"javascript:this.form.pageNo.value='"+page.getPreviousPage()+"';this.form.submit();\" />";
            
            String nextButton = "<input class=\"navbar\" type=\"button\" value=\"下一页\" " + nextDisabled +
                                " "
                                +
                    "onClick=\"javascript:this.form.pageNo.value='"+page.getNextPage()+"';this.form.submit();\" />";
            
            String pageNoInput = "<input class=\"navbar\" type=\"text\" style=\"width:56px;\" value='" + page.getCurrentPageNo() +
                                 "' "
                                 + "onChange=\"javascript:this.form.pageNo.value=this.value\" />";

            bar = "每页pageSize条记录 | \n"
                  + "共pages页/total条记录 | \n"
                  + "\n prev \n next \n | 第pageNoTag页\n"
                  + " <input class=\"navbar\" type=\"submit\" value=\"go\" />\n";

            bar = StringUtils.replace(bar, "pageSize", pageSizeInput);
            bar = StringUtils.replace(bar, "pages", String.valueOf(page.getTotalPageCount()));
            bar = StringUtils.replace(bar, "total", String.valueOf(page.getTotalSize()));
            bar = StringUtils.replace(bar, "prev", prevButton);
            bar = StringUtils.replace(bar, "next", nextButton);
            bar = StringUtils.replace(bar, "pageNoTag", pageNoInput);
        }       
        
        //-------------------------文字型----------------------------//
        if (type.equalsIgnoreCase("SIMPLETEXT")) {
            String pageSizeInput = "<input type=\"text\" style=\"width:24px;\" maxlength=\"2\" value=\"" + page.getPageSize() + "\" "
                                   + "onChange=\"javascript:this.form.pageSize.value=this.value;this.form.submit();\" />";
            String firstText = "首  页";
            String prevText = "上一页";
            String nextText = "下一页";
            String lastText = "末一页";
            if (prevDisabled.equalsIgnoreCase("")) {
                firstText = "<a href=\"javascript:void(0);\" "
                            +
                        "onClick=\"javascript:document.pagerform.pageNo.value='1';document.pagerform.submit();return false;\">"
                            + "首  页"
                            + "</a>";
                prevText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getPreviousPage()+"';document.pagerform.submit();return false;\">"
                           + "上一页"
                           + "</a>";
            }
            if (nextDisabled.equalsIgnoreCase("")) {
                nextText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getNextPage()+"';document.pagerform.submit();return false;\">"
                           + "下一页"
                           + "</a>";
                lastText = "<a href=\"javascript:void(0);\" "
                           +
                        "onClick=\"javascript:document.pagerform.pageNo.value='"+page.getTotalPageCount()+"';document.pagerform.submit();return false;\">"
                           + "末一页"
                           + "</a>";
            }
            String pageNoInput = "<input type=\"text\" style=\"width:56px;\" value='" + page.getCurrentPageNo() + "' "
                                 + "onChange=\"javascript:this.form.pageNo.value=this.value\" />";

            bar = "每页pageSize条记录 | \n"
                  + "共pages页/total条记录 | \n"
                  + "prev \n next \n | 第pageNoTag页\n"
                  +
                    " <input type=\"submit\" value=\"go\" />\n";

            bar = StringUtils.replace(bar, "pageSize", pageSizeInput);
            bar = StringUtils.replace(bar, "pages", String.valueOf(page.getTotalPageCount()));
            bar = StringUtils.replace(bar, "total", String.valueOf(page.getTotalSize()));
            bar = StringUtils.replace(bar, "prev", prevText);
            bar = StringUtils.replace(bar, "next", nextText);
            bar = StringUtils.replace(bar, "pageNoTag", pageNoInput);
        }
        if (type.equalsIgnoreCase("NUMBERTEXT")) {
        	bar=getPageNumberText(page);
        }
        output.append(paramBuf);
        output.append(bar);
        output.append("</form>\n");
		return output.toString();
	}
	/**
	 * 按钮型的导航条
	 * @param request
	 * @param page
	 * @return
	 */
	public static String getPageNaviButton(HttpServletRequest request,PageBean page){
		return getPageNavi(request, page, "BUTTON");
	}
	/**
	 * 简单按钮型的导航条
	 * @param request
	 * @param page
	 * @return
	 */
	public static String getPageNaviSimpleButton(HttpServletRequest request,PageBean page){
		return getPageNavi(request, page, "SIMPLEBUTTON");
	}
	/**
	 * 文字型导航条
	 * @param request
	 * @param page
	 * @return
	 */
	public static String getPageNaviText(HttpServletRequest request,PageBean page){
		return getPageNavi(request, page, "TEXT");
	}
	/**
	 * 简单文字型导航条
	 * @param request
	 * @param page
	 * @return
	 */
	public static String getPageNaviSimpleText(HttpServletRequest request,PageBean page){
		return getPageNavi(request, page, "SIMPLETEXT");
	}
	
	
	public static String getPageNaviNumberText(HttpServletRequest request,PageBean page){
		return getPageNavi(request, page, "NUMBERTEXT");
	}
	
	private static String getPageNumberText(PageBean page){
		//加入数字导航
		int start1=1;
		int end1=2;
		int start2=1;
		int end2=0;
		int start3=page.getTotalPageCount()-1;
		start3=start3<=0?1:start3;
		int end3=page.getTotalPageCount();
		StringBuffer strbf=new StringBuffer();
		String numTag="<input type=\"button\" value=\"numTag\" onClick=\"javascript:this.form.pageNo.value='numTag';this.form.submit();\" />";
		String splitTag="..";
		int nextEnd=page.getCurrentPageNo()+NUM_NEXT;
		int preEnd=page.getCurrentPageNo()-NUM_PRE;
		
		if(preEnd>2){
			start2=preEnd;
		}else{
			end1=0;
		}
		
		if(nextEnd<start3){
			end2=nextEnd;
		}else{
			end2=end3;
			end3=0;
		}
		
		for (int i = start1; i <= end1; i++) {
			strbf.append(StringUtils.replace(numTag, "numTag", String.valueOf(i)));
		}
		if(end1==2){
			strbf.append(splitTag);
		}
		
		for (int i = start2; i <= end2; i++) {
			strbf.append(StringUtils.replace(numTag, "numTag", String.valueOf(i)));
		}		
		if(end3!=0){
			strbf.append(splitTag);
		}
		for (int i = start3; i <= end3; i++) {
			strbf.append(StringUtils.replace(numTag, "numTag", String.valueOf(i)));
		}
		
		return strbf.toString();
	}
}
