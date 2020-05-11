package com.factory.boot.config.listener.listener;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.factory.boot.config.SpringContextHolder;
import com.factory.boot.config.listener.context.ServletContextMonitor;

@Component
public class ServerContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new SpringContextHolder()
				.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()));
		Map<String, ServletContextMonitor> map = SpringContextHolder.getBeansOfType(ServletContextMonitor.class);
		for (ServletContextMonitor monitor : map.values()) {
			monitor.init(sce.getServletContext());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Map<String, ServletContextMonitor> map = SpringContextHolder.getBeansOfType(ServletContextMonitor.class);
		for (ServletContextMonitor monitor : map.values()) {
			monitor.destroyed(sce.getServletContext());
		}
	}

}
