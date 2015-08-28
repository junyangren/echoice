package org.echoice.mgr.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.echoice.mgr.entity.ResultCode;
import org.echoice.mgr.service.ResultCodeService;
import org.echoice.modules.web.MsgTip;
import org.echoice.modules.web.Servlets;
import org.echoice.modules.web.json.EasyUIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
/**
 * 结果码配置管理（使用annotation方式配置 演示）
 * @author wujy
 *
 */
@Controller
@RequestMapping(value = "/console/resultCode")
public class ResultCodeController{
	private static final Logger logger=LoggerFactory.getLogger(ResultCodeController.class);
	private static final String PAGE_SIZE = "20";
	@Autowired
	private ResultCodeService resultCodeService;
	
	@RequestMapping()
	public String index(){
		return "/console/resultCode/resultCodeList.jsp";
	}
	
	@RequestMapping(value = "searchJSON")
	@ResponseBody
	public String searchJSON(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,ServletRequest request) {
		logger.info("aa11111");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<ResultCode> page=resultCodeService.findPageList(searchParams, pageNumber, pageSize);
		String respStr=EasyUIUtil.getGridJSON(page.getTotalElements(), page.getContent(), null);
		return respStr;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm() {
		return "/console/resultCode/resultCodeForm.jsp";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String code, Model model) {
		model.addAttribute("listone", resultCodeService.getResultCodeDao().findOne(code));
		return "/console/resultCode/resultCodeForm.jsp";
	}
	
	@RequestMapping(value = "update",method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid ResultCode resultCode,ServletRequest request) {
		MsgTip msgTip=new MsgTip();
		try{
			resultCodeService.getResultCodeDao().save(resultCode);
		}catch (Exception e) {
			// TODO: handle exception
			msgTip.setCode(4002);
			msgTip.setMsg("异常："+e.getMessage());
		}
		String respStr=JSON.toJSONString(msgTip);
		return respStr;
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public MsgTip delete(@RequestParam String selIds) {
		MsgTip msgTip=new MsgTip();
		try{
			List<String> codeList=JSON.parseArray(selIds, String.class);
			resultCodeService.batchDelete(codeList);
		}catch (Exception e) {
			// TODO: handle exception
			msgTip.setCode(4002);
			msgTip.setMsg("异常："+e.getMessage());
		}
		return msgTip;
	}
}
