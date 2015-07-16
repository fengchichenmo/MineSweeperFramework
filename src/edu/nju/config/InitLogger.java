package edu.nju.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class InitLogger {
	public static void init()
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
	}
}
