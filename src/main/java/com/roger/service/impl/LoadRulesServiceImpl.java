package com.roger.service.impl;

import com.roger.service.LoadRulesService;

public class LoadRulesServiceImpl implements LoadRulesService {

	public LoadRulesServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public String getRulesFromDb(){
		return "/restapi/auth/**= anon \n /restapi/** =  anon";
	}

}
