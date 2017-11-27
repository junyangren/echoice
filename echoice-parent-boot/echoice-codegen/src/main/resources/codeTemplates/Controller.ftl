package ${package_name}.mgr.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.echoice.modules.cas.CasUtil;
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
import ${package_name}.domain.${table_name};
import ${package_name}.mgr.service.${table_name}Service;
/**
* 描述：${table_annotation} 控制层
* @author ${author}
* @date ${date}
*/
@Controller
@RequestMapping(value = "/console/${table_name?uncap_first}")
public class ${table_name}Controller{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private static final String PAGE_SIZE = "20";
	@Autowired
	private ${table_name}Service ${table_name?uncap_first}Service;
	
	@RequestMapping()
	public String index(){
		return "/console/${table_name?uncap_first}/${table_name?uncap_first}List";
	}
	
	@RequestMapping(value = "searchJSONHQL")
	@ResponseBody
	public String searchJSONHQL(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<${table_name}> page=${table_name?uncap_first}Service.findPageList(searchParams, pageNumber, pageSize);
		String respStr=EasyUIUtil.getGridJSON(page.getTotalElements(), page.getContent(), null);
		logger.debug("respStr:{}",respStr);
		return respStr;
	}

    @RequestMapping(value = "searchJSON")
    @ResponseBody
    public String searchJSON(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,${table_name} searchForm,ServletRequest request) {
        Page<${table_name}> page=${table_name?uncap_first}Service.get${table_name}Dao().findPageList(searchForm, pageNumber, pageSize);
        String respStr=EasyUIUtil.getGridJSON(page.getTotalElements(), page.getContent(), null);
        logger.debug("respStr:{}",respStr);
        return respStr;
    }

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm() {
		return "/console/${table_name?uncap_first}/${table_name?uncap_first}Form";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") ${pkColumn.javaType} id, Model model) {
		model.addAttribute("listone", ${table_name?uncap_first}Service.get${table_name}Dao().findOne(id));
		return "/console/${table_name?uncap_first}/${table_name?uncap_first}Form";
	}
	
	@RequestMapping(value = "update",method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid ${table_name} ${table_name?uncap_first},ServletRequest request) {
		MsgTip msgTip=new MsgTip();
		try{
			Date now=new Date();
			${table_name} ${table_name?uncap_first}db=null;
			if(${table_name?uncap_first}.getId()!=null){
				${table_name?uncap_first}db=${table_name?uncap_first}Service.get${table_name}Dao().findOne(${table_name?uncap_first}.getId());
			}
			
			if(${table_name?uncap_first}db!=null){
				${table_name?uncap_first}.setCreateUser(deviceInfodb.getCreateUser());
				${table_name?uncap_first}.setCreateTime(deviceInfodb.getCreateTime());
			}else{
				${table_name?uncap_first}.setCreateUser(CasUtil.getUserName());
				${table_name?uncap_first}.setCreateTime(now);
			}
			${table_name?uncap_first}.setOpUser(CasUtil.getUserName());
			${table_name?uncap_first}.setOpTime(now);		
			${table_name?uncap_first}Service.get${table_name}Dao().save(${table_name?uncap_first});
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("异常：",e);
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
			List<${pkColumn.javaType}> codeList=JSON.parseArray(selIds, ${pkColumn.javaType}.class);
			${table_name?uncap_first}Service.batchDelete(codeList);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("异常：",e);
			msgTip.setCode(4002);
			msgTip.setMsg("异常："+e.getMessage());
		}
		return msgTip;
	}
}
