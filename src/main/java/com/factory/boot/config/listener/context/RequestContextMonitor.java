package com.factory.boot.config.listener.context;

import javax.servlet.ServletRequestEvent;

public interface RequestContextMonitor {

	public void init(ServletRequestEvent evt);

	public void destroyed(ServletRequestEvent evt);
}
