/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-30 17:16:03
 * @LastEditTime: 2019-09-02 16:03:39
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @name Swagger2.java
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	/**
	 * 
	 * <p>
	 * 创建API应用 apiInfo() 增加API相关信息
	 * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
	 * 本例采用指定扫描的包路径来定义指定要建立API的目录。
	 * 
	 * </p>
	 *
	 * @return Docket
	 * @return
	 */
	@Bean
	public Docket createControllerRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("App interface doc").select()
				.apis(RequestHandlerSelectors.basePackage("com.factory.boot.controller.front")).paths(PathSelectors.any())
				.build();
	}

	/**
	 * 
	 * <p>
	 * 创建该API的基本信息（这些基本信息会展现在文档页面中） 访问地址：http://项目实际地址/swagger-ui.html
	 * </p>
	 *
	 * @return ApiInfo
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("更多请关注http://www.baidu.com").termsOfServiceUrl("http://www.baidu.com")
				.contact("Mida Developer").version("1.0").build();
	}

}
