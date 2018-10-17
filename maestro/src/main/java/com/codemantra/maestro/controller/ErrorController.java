package com.codemantra.maestro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping(value="error")
public class ErrorController {

	@RequestMapping(value="{theString}")
	public ModelAndView erro(@PathVariable String theString) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/"+theString);
		return mav;
	}
}
