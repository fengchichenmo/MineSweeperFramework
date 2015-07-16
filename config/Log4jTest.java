package edu.cn.config;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;


public class Log4jTest {


	private static   Logger logger = LogManager.getLogger(Log4jTest.class);
	
	public static void log(){   
	    logger.debug("Debug info.");
	    logger.info("Info info");
	    logger.warn("Warn info");
	    logger.error("Error info");
	    logger.fatal("Fatal info");
	 }
	public static void main(String[] args)
	{
		File file = new File("config//log4j2.xml");
		try {
		    LoggerContext context =(LoggerContext)LogManager.getContext(false);
		    context.setConfigLocation(file.toURI());
		    
		    //重新初始化Log4j2的配置上下文
		    context.reconfigure();
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		log();
	}
}