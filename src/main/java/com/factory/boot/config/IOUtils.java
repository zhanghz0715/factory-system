package com.factory.boot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @name IOUtils.java 对字节流inputStream,outStream,writer,reader字符流的常用方法的使用
 */
public class IOUtils extends org.apache.commons.io.IOUtils {
	public IOUtils() {
	}

	public static FileInputStream getFileInputStream(String filepath) {
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(filepath);
		} catch (FileNotFoundException var3) {
			System.out.println("错误信息:文件不存在");
			var3.printStackTrace();
		}

		return fileInputStream;
	}

	public static FileInputStream getFileInputStream(File file) {
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException var3) {
			System.out.println("错误信息:文件不存在");
			var3.printStackTrace();
		}

		return fileInputStream;
	}

	public static FileOutputStream getFileOutputStream(File file, boolean append) {
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(file, append);
		} catch (FileNotFoundException var4) {
			System.out.println("错误信息:文件不存在");
			var4.printStackTrace();
		}

		return fileOutputStream;
	}

	public static FileOutputStream getFileOutputStream(String filepath, boolean append) {
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(filepath, append);
		} catch (FileNotFoundException var4) {
			System.out.println("错误信息:文件不存在");
			var4.printStackTrace();
		}

		return fileOutputStream;
	}
}
