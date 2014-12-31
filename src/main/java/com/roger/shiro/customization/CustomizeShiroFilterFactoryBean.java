package com.roger.shiro.customization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;

import com.roger.service.impl.LoadRulesServiceImpl;

public class CustomizeShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private static Logger logger =	 LogManager.getLogger(CustomizeShiroFilterFactoryBean.class);
	private LoadRulesServiceImpl loadRulesService;

	public LoadRulesServiceImpl getLoadRulesService() {
		return loadRulesService;
	}

	public void setLoadRulesService(LoadRulesServiceImpl loadRulesService) {
		this.loadRulesService = loadRulesService;
	}

	public CustomizeShiroFilterFactoryBean() {
		// TODO Auto-generated constructor stub
		super();
	}

	public void setFilterChainDefinitions(String definitions) {
		logger.debug("ccccc");
		Ini ini = new Ini();
		ini.load(loadRulesService.getRulesFromDb());

		Ini.Section section = ini.getSection("urls");
		if (CollectionUtils.isEmpty(section)) {

			section = ini.getSection("");
		}
		setFilterChainDefinitionMap(section);

	}

}
