package org.echoice.codegen.web;

import java.util.HashMap;
import java.util.Map;

import org.echoice.codegen.CodeGenBean;
import org.echoice.codegen.CodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	private CodeGenBean codeGenBean;
	@GetMapping()
	public String index(Model model){
		model.addAttribute("codeGenBean", this.codeGenBean);
		return "index";
	}
	
	@RequestMapping("/generate")
	@ResponseBody
	public Map<String, Object> generate(CodeGenBean codeGenBean){
		Map<String, Object> map=new HashMap<>();
		map.put("code", 0);
		map.put("msg", "成功");
		CodeGenerate codeGenerate=new CodeGenerate(codeGenBean);
		try {
			codeGenerate.generate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", e.getMessage());
		}
		
		return map;
	}
}
