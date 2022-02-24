package br.com.nees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.MembroDao;
import br.com.nees.utils.SenhaPadrao;

@Controller
public class HomeController {


	@GetMapping(value = { "/", "/index" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");
		
		

		return mv;
	}
	
	@GetMapping( "/artigos")
	public ModelAndView artigos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/artigos");

		return mv;
	}
	
	@GetMapping( "/apoiadores")
	public ModelAndView apoiadores() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/apoiadores");

		return mv;
	}
	
	@GetMapping( "/sobre")
	public ModelAndView sobre() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/sobre");

		return mv;
	}

	@GetMapping("/login")
	public ModelAndView telaLogin() {
		ModelAndView mv = new ModelAndView();
		System.out.println("aq" + new BCryptPasswordEncoder().encode(SenhaPadrao.senhaPadrao));
		mv.setViewName("home/login");
		return mv;
	}

}
