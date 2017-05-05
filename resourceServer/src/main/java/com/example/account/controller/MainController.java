package com.example.account.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController
{

	@GetMapping("/")
    public String root() {
        return "redirect:/index";
    }
 
    @GetMapping("/index")
    public String index(Model model) {
        if(model == null ){
            return "index";
        }
        model.addAttribute("model", model);
        return "index";
    }
    @GetMapping("/user")
    public String user(Model model) {
        if(model == null ){
            return "index";
        }
        model.addAttribute("model", model);
        return "index";
    }
    @GetMapping("/authIndex")
    @PreAuthorize("hasAuthority('ROLE_USER')")  // 指定角色权限才能操作方法
    public String authIndex(Model model) {
    	if(model == null ){
    		return "authIndex";
    	}
    	model.addAttribute("model", model);
    	return "authIndex";
    }
 
    @GetMapping("/403")
    public String accesssDenied() {
        return "403";
    }
}
