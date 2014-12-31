package com.roger.api.services;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;


@RestController
public class RestApiController {

	private static Logger Logger = LogManager.getLogger(RestApiController.class);



	@RequestMapping(value="/restapi/billing/rating",produces="text/josn;charset=UTF-8")
	public String getPriceOfProduct(@RequestBody String Jsonata){
		Logger.debug(Jsonata);
		Logger.debug("wa wuna jrebel");
		Resource products = ContextLoader.getCurrentWebApplicationContext().getResource("classpath:products.json");
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(products.getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String content = "";
		StringBuilder sb =  new StringBuilder();
		if (reader != null) {
			try {
				while ((content= reader.readLine())!=null) {
						sb.append(content);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	@RequestMapping(value="/restapi/billing/getproducts", produces="text/json;charset=UTF-8")
	public String getProductList(){
		return "";
	}

	@RequestMapping("/restapi/login")
	public String login(@RequestParam String username, @RequestParam String password){
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"user password not valid\"}";
		}
		return "{\"sucesss\":\"login\"}";



	}
}
