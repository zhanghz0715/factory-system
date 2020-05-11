/**
 * 
 */
package com.factory.boot.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * @name PropertiesUtils.java 启动类读取配置文件
 */
public class PropertiesUtils {
	public static final String[] DEFAULT_CONFIG_FILE = new String[] { "classpath:application.yml",
			"classpath:config/application-dao.yml" };

	private static Logger logger = initLogger();

	private final Properties properties = new Properties();

	private static Pattern p1 = Pattern.compile("\\$\\{.*?\\}");

	public PropertiesUtils(String... configFiles) {
		String[] var2 = configFiles;
		int var3 = configFiles.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String location = var2[var4];

			try {
				Resource resource = ResourceUtils.getResource(location);
				if (resource.exists()) {
					String ext = getFileExtension(location);
					if ("properties".equals(ext)) {
						InputStreamReader is = null;

						try {
							is = new InputStreamReader(resource.getInputStream(), "UTF-8");
							this.properties.load(is);
						} catch (IOException var14) {
							logger.error("Load " + location + " failure. ", var14);
						} finally {
							IOUtils.closeQuietly(is);
						}
					} else if ("yml".equals(ext)) {
						YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
						bean.setResources(new Resource[] { resource });
						Iterator var9 = bean.getObject().entrySet().iterator();

						while (var9.hasNext()) {
							Entry<Object, Object> entry = (Entry) var9.next();
							this.properties.put(ObjectUtils.toString(entry.getKey()),
									ObjectUtils.toString(entry.getValue()));
						}
					}
				}
			} catch (Exception var16) {
				logger.error("Load " + location + " failure. ", var16);
			}
		}
	}

	public Properties getProperties() {
		return this.properties;
	}

	public static PropertiesUtils getInstance() {
		return PropertiesUtils.PropertiesLoaderHolder.INSTANCE;
	}

	public static void releadInstance() {
		PropertiesUtils.PropertiesLoaderHolder.releadInstance();
	}

	public String getProperty(String key) {
		String value = this.properties.getProperty(key);
		if (value == null) {
			String systemProperty = System.getProperty(key);
			return systemProperty != null ? systemProperty : null;
		} else {
			String g;
			String keyChild;
			for (Matcher m = p1.matcher(value); m.find(); value = value.replace(g, this.getProperty(keyChild))) {
				g = m.group();
				keyChild = g.replaceAll("\\$\\{", "").replaceAll("\\}", "");
			}

			return value;
		}
	}

	public String getProperty(String key, String defaultValue) {
		String value = this.getProperty(key);
		return value != null ? value : defaultValue;
	}

	private static Logger initLogger() {
		String logPath = null;

		try {
			logPath = (new DefaultResourceLoader()).getResource("/").getFile().getPath();
		} catch (Exception var2) {
			logPath = System.getProperty("user.dir");
		}

		String classesLogPath = path(logPath + "/WEB-INF/classes");
		if ((new File(classesLogPath)).exists()) {
			logPath = classesLogPath;
		}

		System.setProperty("logPath", path(logPath));
		return LoggerFactory.getLogger(PropertiesUtils.class);
	}

	private static final class PropertiesLoaderHolder {
		private static PropertiesUtils INSTANCE;

		private PropertiesLoaderHolder() {
		}

		public static void releadInstance() {
			Set<String> configFiles = new LinkedHashSet<>();
			Resource[] resources = ResourceUtils.getResources("classpath*:/config/system-*.*");
			Resource[] var2 = resources;
			int var3 = resources.length;

			int var4;
			for (var4 = 0; var4 < var3; ++var4) {
				Resource resource = var2[var4];
				configFiles.add("classpath:/config/" + resource.getFilename());
			}

			String[] var6 = PropertiesUtils.DEFAULT_CONFIG_FILE;
			var3 = var6.length;

			for (var4 = 0; var4 < var3; ++var4) {
				String configFile = var6[var4];
				configFiles.add(configFile);
			}

			PropertiesUtils.logger.debug("Loading project config: {}", configFiles);
			INSTANCE = new PropertiesUtils((String[]) configFiles.toArray(new String[configFiles.size()]));
		}

		static {
			releadInstance();
		}
	}

	public static String getFileExtension(String fileName) {
		return fileName != null && fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != fileName.length() - 1
				? StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1))
				: null;
	}

	public static String path(String path) {
		String p = StringUtils.replace(path, "\\", "/");
		p = StringUtils.join(StringUtils.split(p, "/"), "/");
		if (!StringUtils.startsWithAny(p, new CharSequence[] { "/" })
				&& StringUtils.startsWithAny(path, new CharSequence[] { "\\", "/" })) {
			p = p + "/";
		}

		if (!StringUtils.endsWithAny(p, new CharSequence[] { "/" })
				&& StringUtils.endsWithAny(path, new CharSequence[] { "\\", "/" })) {
			p = p + "/";
		}

		if (path != null && path.startsWith("/")) {
			p = "/" + p;
		}

		return p;
	}

}
