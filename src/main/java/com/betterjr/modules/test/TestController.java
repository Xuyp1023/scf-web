package com.betterjr.modules.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.betterjr.common.exception.BytterException;
import com.betterjr.common.web.AjaxObject;

@Controller
@RequestMapping(value = "/testController")
public class TestController {

	@Reference(interfaceClass = IDemoService.class)
	private IDemoService demoService;

	@RequestMapping(value = "/testString", method = RequestMethod.GET)
	public @ResponseBody String testQuery(HttpServletRequest request, String in) {
		// call dubbo service
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("home", "深圳");
			String result = "";
			result = demoService.webQueryEntity(map, in);
			System.out.println(result);
			return "crontroller+" + result;
		} catch (RpcException btEx) {
			if(btEx.getCause()!=null && btEx.getCause() instanceof BytterException){
				return AjaxObject.newError(btEx.getCause().getMessage()).toJson();
			}
			return AjaxObject.newError("service failed").toJson();
		} catch (Exception ex) {
			return AjaxObject.newError("service failed").toJson();
		}

	}

	@RequestMapping(value = "/testAdd", method = RequestMethod.GET)
	public @ResponseBody String testAdd(HttpServletRequest request, String name) {
		// call dubbo service
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("home", "深圳");
			String result = "save ok";
			demoService.webAddEntity(map, name);
			System.out.println(result);
			return "crontroller+" + result;
		} catch (Exception ex) {
			return AjaxObject.newError("save failed").toJson();
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(String username, String password) {
		System.out.println("controller " + username + ":" + password);
		return "/static/error.html";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "/static/error.html";
	}

}
