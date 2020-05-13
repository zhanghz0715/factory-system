/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-30 17:16:03
 * @LastEditTime: 2019-09-28 11:18:58
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import org.junit.Test;

/**
 * @name CodeGeneration.java
 * @Description: 代码生成器
 */
public class CodeGeneration {
	/**
	 * 基础包名
	 */
	private static String PACKAGENAME = "com.factory.boot";
	/**
	 * controller、dao、serviceimpl、mapper等命名前缀
	 */
	private static String PRENAME = "%s";

	/**
	 * 要生成的表名
	 */
	private static String[] DBNAME = new String[] { "t_menu" };

	/**
	 * 需要去掉的表前缀明细,和 DBNAME 对应
	 */
	private static String[] TABLEPREFIX = new String[] { "t_" };

	/**
	 * 文件输出的路径
	 */
	private static String OUTPUTDIR = "C:/代码";

	 @Test
	public void generateCode() {
		generateByTables(PRENAME, DBNAME);
	}

	private void generateByTables(String name, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		String dbUrl = "jdbc:mysql://47.104.252.174:3306/factory";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("123456")
				.setDriverName("com.mysql.jdbc.Driver");
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig
				// .setCapitalMode(true) // 全局大写命名 ORACLE 注意
				.setEntityLombokModel(true).setTablePrefix(TABLEPREFIX).setDbColumnUnderline(true)
				.setNaming(NamingStrategy.underline_to_camel).setInclude(tableNames)// 修改替换成你需要的表名，多个表名传数组
				.setSuperControllerClass("com.factory.boot.config.BaseController")
				.setSuperEntityClass("com.factory.boot.config.BaseEntity")
				.setSuperMapperClass("com.factory.boot.config.SuperMapper")
				.setSuperEntityColumns(new String[] { "id", "createTime","updateTime","isDelete" });

		config.setActiveRecord(false).setAuthor("").setOutputDir(OUTPUTDIR).setFileOverride(true)

				.setControllerName(name + "Controller").setServiceName(name + "Service")
				.setServiceImplName(name + "Impl").setEnableCache(false).setMapperName(name + "Dao")
				.setXmlName(name + "Dao");

		new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategyConfig)
				.setPackageInfo(new PackageConfig().setParent(PACKAGENAME).setController("controller").setMapper("dao")
						.setEntity("model"))
				.execute();
	}

}
