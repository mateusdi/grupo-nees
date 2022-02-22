package br.com.nees.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/home")
public class HomeController {

	@GetMapping(value = { "", "index" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/home/index");

		return mv;
	}

	@GetMapping("/login")
	public ModelAndView telaLogin() {
		ModelAndView mv = new ModelAndView();
		System.out.println("aq" + new BCryptPasswordEncoder().encode("123"));
		mv.setViewName("/home/login");
		return mv;
	}

}
