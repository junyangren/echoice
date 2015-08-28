package org.echoice.mgr.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/demo")
public class DemoController {
	private Logger logger=LoggerFactory.getLogger(DemoController.class);
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		logger.info("test");
		model.addAttribute("action", "createForm");
		return "demo/demoForm";
	}
}
