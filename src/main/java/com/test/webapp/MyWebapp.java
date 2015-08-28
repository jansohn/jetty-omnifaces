package com.test.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class MyWebapp implements ServletContextListener
{
	private static final Logger log = LoggerFactory.getLogger(MyWebapp.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		log.info("Servlet context is being initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		log.info("Servlet context is being destroyed!");
	}

}
