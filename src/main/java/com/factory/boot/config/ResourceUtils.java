package com.factory.boot.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @name ResourceUtils.java 根据路径加载资源工具类
 */
public class ResourceUtils extends org.springframework.util.ResourceUtils {
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	public ResourceUtils() {
	}

	public static ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public static ClassLoader getClassLoader() {
		return resourceLoader.getClassLoader();
	}

	public static Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	public static InputStream getResourceFileStream(String location) throws IOException {
		Resource resource = resourceLoader.getResource(location);
		return resource.getInputStream();
	}

	public static String getResourceFileContent(String location) {
		InputStream is = null;

		String var2;
		try {
			is = getResourceFileStream(location);
			var2 = IOUtils.toString(is, "UTF-8");
		} catch (IOException var6) {
			throw new RuntimeException(var6);
		} finally {
			IOUtils.closeQuietly(is);
		}

		return var2;
	}

	public static Resource[] getResources(String locationPattern) {
		try {
			Resource[] resources = (new PathMatchingResourcePatternResolver()).getResources(locationPattern);
			return resources;
		} catch (IOException var2) {
			throw new RuntimeException(var2);
		}
	}
}
