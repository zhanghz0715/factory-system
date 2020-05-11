/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-09-05 11:34:20
 * @LastEditTime: 2019-09-30 10:30:59
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot;

import com.factory.boot.config.PropertiesUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		application.properties(PropertiesUtils.getInstance().getProperties());
		return application.sources(DemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setDefaultProperties(PropertiesUtils.getInstance().getProperties());
		
		app.run(args);
	}

}
